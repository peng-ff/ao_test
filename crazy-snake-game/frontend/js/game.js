// 游戏配置
const GAME_CONFIG = {
    GRID_SIZE: 20,
    CANVAS_WIDTH: 800,
    CANVAS_HEIGHT: 600,
    INITIAL_SNAKE_LENGTH: 3,
    STAGE_DURATION: 30, // 每个阶段持续30秒
    STAGE_CONFIGS: [
        { stage: 1, speed: 150, obstacles: 3, itemInterval: [8000, 12000], scoreMultiplier: 1.0 },
        { stage: 2, speed: 135, obstacles: 5, itemInterval: [7000, 10000], scoreMultiplier: 1.2 },
        { stage: 3, speed: 120, obstacles: 7, itemInterval: [6000, 9000], scoreMultiplier: 1.5 },
        { stage: 4, speed: 105, obstacles: 10, itemInterval: [5000, 8000], scoreMultiplier: 2.0 },
        { stage: 5, speed: 90, obstacles: 13, itemInterval: [4000, 7000], scoreMultiplier: 2.5 },
        { stage: 6, speed: 75, obstacles: 15, itemInterval: [3000, 6000], scoreMultiplier: 2.5 }
    ],
    ITEM_WEIGHTS: {
        SPEED_UP: 20,
        SPEED_DOWN: 20,
        DOUBLE_SCORE: 10,
        SHRINK_BODY: 15,
        WALL_PASS: 8,
        REVERSE_CONTROL: 7,
        INVINCIBLE: 3
    }
};

// 方向常量
const DIRECTIONS = {
    UP: { x: 0, y: -1 },
    DOWN: { x: 0, y: 1 },
    LEFT: { x: -1, y: 0 },
    RIGHT: { x: 1, y: 0 }
};

// 游戏引擎类
class SnakeGame {
    constructor(canvasId) {
        this.canvas = document.getElementById(canvasId);
        this.ctx = this.canvas.getContext('2d');
        this.reset();
        this.itemConfigs = [];
        this.loadItemConfigs();
    }

    // 加载道具配置
    async loadItemConfigs() {
        try {
            this.itemConfigs = await api.getItemConfigs();
        } catch (error) {
            console.error('加载道具配置失败:', error);
        }
    }

    // 重置游戏状态
    reset() {
        this.snake = [];
        this.direction = DIRECTIONS.RIGHT;
        this.nextDirection = DIRECTIONS.RIGHT;
        this.food = null;
        this.obstacles = [];
        this.items = [];
        this.score = 0;
        this.stage = 1;
        this.gameTime = 0;
        this.itemsCollected = 0;
        this.maxLength = GAME_CONFIG.INITIAL_SNAKE_LENGTH;
        this.isRunning = false;
        this.isPaused = false;
        this.gameLoop = null;
        this.itemSpawnTimer = null;
        this.stageTimer = null;
        this.timeCounter = null;
        this.activeEffects = new Map();
        
        // 初始化蛇
        const startX = Math.floor(GAME_CONFIG.CANVAS_WIDTH / GAME_CONFIG.GRID_SIZE / 2);
        const startY = Math.floor(GAME_CONFIG.CANVAS_HEIGHT / GAME_CONFIG.GRID_SIZE / 2);
        for (let i = 0; i < GAME_CONFIG.INITIAL_SNAKE_LENGTH; i++) {
            this.snake.push({ x: startX - i, y: startY });
        }
        
        this.spawnFood();
        this.initObstacles();
    }

    // 开始游戏
    start() {
        this.isRunning = true;
        this.isPaused = false;
        const config = this.getCurrentStageConfig();
        
        this.gameLoop = setInterval(() => {
            if (!this.isPaused) {
                this.update();
                this.render();
            }
        }, config.speed);

        this.startItemSpawner();
        this.startStageTimer();
        this.startTimeCounter();
    }

    // 暂停/继续游戏
    togglePause() {
        this.isPaused = !this.isPaused;
        return this.isPaused;
    }

    // 停止游戏
    stop() {
        this.isRunning = false;
        if (this.gameLoop) clearInterval(this.gameLoop);
        if (this.itemSpawnTimer) clearInterval(this.itemSpawnTimer);
        if (this.stageTimer) clearInterval(this.stageTimer);
        if (this.timeCounter) clearInterval(this.timeCounter);
    }

    // 获取当前阶段配置
    getCurrentStageConfig() {
        const index = Math.min(this.stage - 1, GAME_CONFIG.STAGE_CONFIGS.length - 1);
        return GAME_CONFIG.STAGE_CONFIGS[index];
    }

    // 更新游戏状态
    update() {
        this.direction = this.nextDirection;
        
        // 移动蛇
        const head = { ...this.snake[0] };
        
        // 应用方向（考虑混乱控制效果）
        if (this.hasEffect('REVERSE_CONTROL')) {
            head.x -= this.direction.x;
            head.y -= this.direction.y;
        } else {
            head.x += this.direction.x;
            head.y += this.direction.y;
        }

        // 检查碰撞
        if (this.checkCollision(head)) {
            this.gameOver();
            return;
        }

        this.snake.unshift(head);

        // 检查是否吃到食物
        if (head.x === this.food.x && head.y === this.food.y) {
            this.handleEatFood();
        }
        // 检查是否吃到道具
        else if (this.checkItemCollision(head)) {
            // 道具碰撞在checkItemCollision中处理
        }
        // 正常移动，移除尾部
        else {
            this.snake.pop();
        }

        // 更新最大长度
        if (this.snake.length > this.maxLength) {
            this.maxLength = this.snake.length;
        }

        // 更新激活效果
        this.updateActiveEffects();
    }

    // 检查碰撞
    checkCollision(head) {
        // 如果有无敌或穿墙效果，不检测碰撞
        if (this.hasEffect('INVINCIBLE') || this.hasEffect('WALL_PASS')) {
            // 穿墙模式：从另一边出现
            if (this.hasEffect('WALL_PASS')) {
                if (head.x < 0) head.x = Math.floor(GAME_CONFIG.CANVAS_WIDTH / GAME_CONFIG.GRID_SIZE) - 1;
                if (head.x >= Math.floor(GAME_CONFIG.CANVAS_WIDTH / GAME_CONFIG.GRID_SIZE)) head.x = 0;
                if (head.y < 0) head.y = Math.floor(GAME_CONFIG.CANVAS_HEIGHT / GAME_CONFIG.GRID_SIZE) - 1;
                if (head.y >= Math.floor(GAME_CONFIG.CANVAS_HEIGHT / GAME_CONFIG.GRID_SIZE)) head.y = 0;
            }
            return false;
        }

        // 检查墙壁碰撞
        if (head.x < 0 || head.x >= Math.floor(GAME_CONFIG.CANVAS_WIDTH / GAME_CONFIG.GRID_SIZE) ||
            head.y < 0 || head.y >= Math.floor(GAME_CONFIG.CANVAS_HEIGHT / GAME_CONFIG.GRID_SIZE)) {
            return true;
        }

        // 检查自身碰撞
        for (let i = 1; i < this.snake.length; i++) {
            if (head.x === this.snake[i].x && head.y === this.snake[i].y) {
                return true;
            }
        }

        // 检查障碍物碰撞
        for (const obstacle of this.obstacles) {
            if (head.x === obstacle.x && head.y === obstacle.y) {
                return true;
            }
        }

        return false;
    }

    // 处理吃到食物
    handleEatFood() {
        const config = this.getCurrentStageConfig();
        let baseScore = 10;
        let multiplier = config.scoreMultiplier;
        
        if (this.hasEffect('DOUBLE_SCORE')) {
            multiplier *= 2;
        }
        
        this.score += Math.floor(baseScore * multiplier);
        this.spawnFood();
        this.updateUI();
    }

    // 检查道具碰撞
    checkItemCollision(head) {
        for (let i = 0; i < this.items.length; i++) {
            const item = this.items[i];
            if (head.x === item.x && head.y === item.y) {
                this.handleItemEffect(item.type);
                this.items.splice(i, 1);
                this.itemsCollected++;
                return true;
            }
        }
        return false;
    }

    // 处理道具效果
    handleItemEffect(itemType) {
        const config = this.getCurrentStageConfig();
        
        switch (itemType) {
            case 'SPEED_UP':
                this.addEffect('SPEED_UP', 5000);
                this.score += Math.floor(5 * config.scoreMultiplier);
                this.adjustSpeed(0.67); // 速度提升50%
                break;
            case 'SPEED_DOWN':
                this.addEffect('SPEED_DOWN', 5000);
                this.score += Math.floor(5 * config.scoreMultiplier);
                this.adjustSpeed(1.43); // 速度降低30%
                break;
            case 'DOUBLE_SCORE':
                this.addEffect('DOUBLE_SCORE', 10000);
                this.score += Math.floor(20 * config.scoreMultiplier * 2);
                break;
            case 'SHRINK_BODY':
                this.score += Math.floor(15 * config.scoreMultiplier);
                const removeCount = Math.min(3, this.snake.length - 3);
                for (let i = 0; i < removeCount; i++) {
                    this.snake.pop();
                }
                break;
            case 'WALL_PASS':
                this.addEffect('WALL_PASS', 8000);
                this.score += Math.floor(30 * config.scoreMultiplier);
                break;
            case 'REVERSE_CONTROL':
                this.addEffect('REVERSE_CONTROL', 7000);
                this.score += Math.floor(25 * config.scoreMultiplier);
                break;
            case 'INVINCIBLE':
                this.addEffect('INVINCIBLE', 6000);
                this.score += Math.floor(50 * config.scoreMultiplier);
                break;
        }
        
        this.updateUI();
    }

    // 添加效果
    addEffect(type, duration) {
        this.activeEffects.set(type, {
            endTime: Date.now() + duration,
            duration: duration
        });
    }

    // 检查是否有某个效果
    hasEffect(type) {
        return this.activeEffects.has(type) && this.activeEffects.get(type).endTime > Date.now();
    }

    // 更新激活效果
    updateActiveEffects() {
        const now = Date.now();
        const effectsToRemove = [];
        
        for (const [type, effect] of this.activeEffects.entries()) {
            if (effect.endTime <= now) {
                effectsToRemove.push(type);
                
                // 速度效果结束时恢复正常速度
                if (type === 'SPEED_UP' || type === 'SPEED_DOWN') {
                    this.restartGameLoop();
                }
            }
        }
        
        effectsToRemove.forEach(type => this.activeEffects.delete(type));
        this.updateEffectsUI();
    }

    // 调整游戏速度
    adjustSpeed(multiplier) {
        clearInterval(this.gameLoop);
        const config = this.getCurrentStageConfig();
        this.gameLoop = setInterval(() => {
            if (!this.isPaused) {
                this.update();
                this.render();
            }
        }, config.speed * multiplier);
    }

    // 重启游戏循环（恢复正常速度）
    restartGameLoop() {
        clearInterval(this.gameLoop);
        const config = this.getCurrentStageConfig();
        this.gameLoop = setInterval(() => {
            if (!this.isPaused) {
                this.update();
                this.render();
            }
        }, config.speed);
    }

    // 生成食物
    spawnFood() {
        let newFood;
        do {
            newFood = {
                x: Math.floor(Math.random() * (GAME_CONFIG.CANVAS_WIDTH / GAME_CONFIG.GRID_SIZE)),
                y: Math.floor(Math.random() * (GAME_CONFIG.CANVAS_HEIGHT / GAME_CONFIG.GRID_SIZE))
            };
        } while (this.isPositionOccupied(newFood));
        
        this.food = newFood;
    }

    // 初始化障碍物
    initObstacles() {
        const config = this.getCurrentStageConfig();
        this.obstacles = [];
        
        for (let i = 0; i < config.obstacles; i++) {
            this.spawnObstacle();
        }
    }

    // 生成障碍物
    spawnObstacle() {
        let newObstacle;
        let attempts = 0;
        do {
            newObstacle = {
                x: Math.floor(Math.random() * (GAME_CONFIG.CANVAS_WIDTH / GAME_CONFIG.GRID_SIZE)),
                y: Math.floor(Math.random() * (GAME_CONFIG.CANVAS_HEIGHT / GAME_CONFIG.GRID_SIZE))
            };
            attempts++;
        } while (this.isPositionOccupied(newObstacle) && attempts < 100);
        
        if (attempts < 100) {
            this.obstacles.push(newObstacle);
        }
    }

    // 开始道具生成器
    startItemSpawner() {
        const spawnItem = () => {
            const config = this.getCurrentStageConfig();
            const [minInterval, maxInterval] = config.itemInterval;
            const interval = minInterval + Math.random() * (maxInterval - minInterval);
            
            if (this.items.length < 3) { // 最多同时存在3个道具
                this.spawnItem();
            }
            
            this.itemSpawnTimer = setTimeout(spawnItem, interval);
        };
        
        spawnItem();
    }

    // 生成道具
    spawnItem() {
        const itemType = this.getRandomItemType();
        let newItem;
        let attempts = 0;
        
        do {
            newItem = {
                x: Math.floor(Math.random() * (GAME_CONFIG.CANVAS_WIDTH / GAME_CONFIG.GRID_SIZE)),
                y: Math.floor(Math.random() * (GAME_CONFIG.CANVAS_HEIGHT / GAME_CONFIG.GRID_SIZE)),
                type: itemType
            };
            attempts++;
        } while (this.isPositionOccupied(newItem) && attempts < 100);
        
        if (attempts < 100) {
            this.items.push(newItem);
        }
    }

    // 根据权重随机获取道具类型
    getRandomItemType() {
        const weights = GAME_CONFIG.ITEM_WEIGHTS;
        const totalWeight = Object.values(weights).reduce((sum, w) => sum + w, 0);
        let random = Math.random() * totalWeight;
        
        for (const [type, weight] of Object.entries(weights)) {
            random -= weight;
            if (random <= 0) {
                return type;
            }
        }
        
        return 'SPEED_UP'; // 默认
    }

    // 检查位置是否被占用
    isPositionOccupied(pos) {
        // 检查蛇身
        for (const segment of this.snake) {
            if (pos.x === segment.x && pos.y === segment.y) return true;
        }
        
        // 检查食物
        if (this.food && pos.x === this.food.x && pos.y === this.food.y) return true;
        
        // 检查障碍物
        for (const obstacle of this.obstacles) {
            if (pos.x === obstacle.x && pos.y === obstacle.y) return true;
        }
        
        // 检查道具
        for (const item of this.items) {
            if (pos.x === item.x && pos.y === item.y) return true;
        }
        
        return false;
    }

    // 开始阶段计时器
    startStageTimer() {
        this.stageTimer = setInterval(() => {
            if (!this.isPaused) {
                this.advanceStage();
            }
        }, GAME_CONFIG.STAGE_DURATION * 1000);
    }

    // 推进阶段
    advanceStage() {
        this.stage++;
        this.updateUI();
        
        // 增加障碍物
        const config = this.getCurrentStageConfig();
        while (this.obstacles.length < config.obstacles) {
            this.spawnObstacle();
        }
        
        // 调整速度
        this.restartGameLoop();
    }

    // 开始时间计数器
    startTimeCounter() {
        this.timeCounter = setInterval(() => {
            if (!this.isPaused) {
                this.gameTime++;
                this.score += Math.floor(1 * this.getCurrentStageConfig().scoreMultiplier);
                this.updateUI();
            }
        }, 1000);
    }

    // 更新UI
    updateUI() {
        document.getElementById('score').textContent = this.score;
        document.getElementById('stage').textContent = this.stage;
        document.getElementById('length').textContent = this.snake.length;
        document.getElementById('time').textContent = this.gameTime;
    }

    // 更新效果UI
    updateEffectsUI() {
        const effectsDiv = document.getElementById('activeEffects');
        effectsDiv.innerHTML = '';
        
        for (const [type, effect] of this.activeEffects.entries()) {
            if (this.hasEffect(type)) {
                const badge = document.createElement('div');
                badge.className = 'effect-badge';
                badge.textContent = this.getEffectName(type);
                effectsDiv.appendChild(badge);
            }
        }
    }

    // 获取效果名称
    getEffectName(type) {
        const names = {
            SPEED_UP: '⚡加速',
            SPEED_DOWN: '❄️减速',
            DOUBLE_SCORE: '⭐双倍',
            WALL_PASS: '🛡️穿墙',
            REVERSE_CONTROL: '🌀混乱',
            INVINCIBLE: '🌈无敌'
        };
        return names[type] || type;
    }

    // 渲染游戏
    render() {
        // 清空画布
        this.ctx.fillStyle = '#f5f5f5';
        this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);

        // 绘制网格
        this.ctx.strokeStyle = '#e0e0e0';
        this.ctx.lineWidth = 0.5;
        for (let i = 0; i <= GAME_CONFIG.CANVAS_WIDTH; i += GAME_CONFIG.GRID_SIZE) {
            this.ctx.beginPath();
            this.ctx.moveTo(i, 0);
            this.ctx.lineTo(i, GAME_CONFIG.CANVAS_HEIGHT);
            this.ctx.stroke();
        }
        for (let i = 0; i <= GAME_CONFIG.CANVAS_HEIGHT; i += GAME_CONFIG.GRID_SIZE) {
            this.ctx.beginPath();
            this.ctx.moveTo(0, i);
            this.ctx.lineTo(GAME_CONFIG.CANVAS_WIDTH, i);
            this.ctx.stroke();
        }

        // 绘制蛇
        this.snake.forEach((segment, index) => {
            if (index === 0) {
                // 蛇头
                if (this.hasEffect('INVINCIBLE')) {
                    this.ctx.fillStyle = 'gold';
                } else {
                    this.ctx.fillStyle = '#4CAF50';
                }
            } else {
                this.ctx.fillStyle = '#8BC34A';
            }
            this.ctx.fillRect(
                segment.x * GAME_CONFIG.GRID_SIZE + 1,
                segment.y * GAME_CONFIG.GRID_SIZE + 1,
                GAME_CONFIG.GRID_SIZE - 2,
                GAME_CONFIG.GRID_SIZE - 2
            );
        });

        // 绘制食物
        if (this.food) {
            this.ctx.fillStyle = '#FF5722';
            this.ctx.beginPath();
            this.ctx.arc(
                this.food.x * GAME_CONFIG.GRID_SIZE + GAME_CONFIG.GRID_SIZE / 2,
                this.food.y * GAME_CONFIG.GRID_SIZE + GAME_CONFIG.GRID_SIZE / 2,
                GAME_CONFIG.GRID_SIZE / 2 - 2,
                0,
                Math.PI * 2
            );
            this.ctx.fill();
        }

        // 绘制障碍物
        this.ctx.fillStyle = '#757575';
        this.obstacles.forEach(obstacle => {
            this.ctx.fillRect(
                obstacle.x * GAME_CONFIG.GRID_SIZE + 1,
                obstacle.y * GAME_CONFIG.GRID_SIZE + 1,
                GAME_CONFIG.GRID_SIZE - 2,
                GAME_CONFIG.GRID_SIZE - 2
            );
        });

        // 绘制道具
        this.items.forEach(item => {
            const color = this.getItemColor(item.type);
            this.ctx.fillStyle = color;
            this.ctx.fillRect(
                item.x * GAME_CONFIG.GRID_SIZE + 2,
                item.y * GAME_CONFIG.GRID_SIZE + 2,
                GAME_CONFIG.GRID_SIZE - 4,
                GAME_CONFIG.GRID_SIZE - 4
            );
            
            // 绘制道具图标
            this.ctx.fillStyle = 'white';
            this.ctx.font = '12px Arial';
            this.ctx.textAlign = 'center';
            this.ctx.textBaseline = 'middle';
            this.ctx.fillText(
                this.getItemIcon(item.type),
                item.x * GAME_CONFIG.GRID_SIZE + GAME_CONFIG.GRID_SIZE / 2,
                item.y * GAME_CONFIG.GRID_SIZE + GAME_CONFIG.GRID_SIZE / 2
            );
        });
    }

    // 获取道具颜色
    getItemColor(type) {
        const colors = {
            SPEED_UP: '#f44336',
            SPEED_DOWN: '#2196F3',
            DOUBLE_SCORE: '#FFD700',
            SHRINK_BODY: '#9C27B0',
            WALL_PASS: '#4CAF50',
            REVERSE_CONTROL: '#FF9800',
            INVINCIBLE: '#E91E63'
        };
        return colors[type] || '#999';
    }

    // 获取道具图标
    getItemIcon(type) {
        const icons = {
            SPEED_UP: '⚡',
            SPEED_DOWN: '❄️',
            DOUBLE_SCORE: '⭐',
            SHRINK_BODY: '✂️',
            WALL_PASS: '🛡️',
            REVERSE_CONTROL: '🌀',
            INVINCIBLE: '🌈'
        };
        return icons[type] || '?';
    }

    // 改变方向
    changeDirection(newDirection) {
        // 防止反向移动
        if ((newDirection === DIRECTIONS.UP && this.direction !== DIRECTIONS.DOWN) ||
            (newDirection === DIRECTIONS.DOWN && this.direction !== DIRECTIONS.UP) ||
            (newDirection === DIRECTIONS.LEFT && this.direction !== DIRECTIONS.RIGHT) ||
            (newDirection === DIRECTIONS.RIGHT && this.direction !== DIRECTIONS.LEFT)) {
            this.nextDirection = newDirection;
        }
    }

    // 游戏结束
    gameOver() {
        this.stop();
        this.onGameOver && this.onGameOver({
            score: this.score,
            maxLength: this.maxLength,
            gameDuration: this.gameTime,
            maxStage: this.stage,
            itemsCollected: this.itemsCollected
        });
    }

    // 获取游戏状态
    getGameState() {
        return {
            score: this.score,
            maxLength: this.maxLength,
            gameDuration: this.gameTime,
            maxStage: this.stage,
            itemsCollected: this.itemsCollected
        };
    }
}
