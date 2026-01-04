// 主控制逻辑
let game = null;
let currentPage = 'auth';

// 页面初始化
document.addEventListener('DOMContentLoaded', () => {
    initializeApp();
    setupEventListeners();
});

// 初始化应用
function initializeApp() {
    if (api.isLoggedIn()) {
        showPage('menu');
        updateUserInfo();
    } else {
        showPage('auth');
    }
}

// 设置事件监听器
function setupEventListeners() {
    // 认证相关
    document.getElementById('showRegister').addEventListener('click', (e) => {
        e.preventDefault();
        document.querySelector('.auth-box:first-child').style.display = 'none';
        document.getElementById('registerBox').style.display = 'block';
    });

    document.getElementById('showLogin').addEventListener('click', (e) => {
        e.preventDefault();
        document.querySelector('.auth-box:first-child').style.display = 'block';
        document.getElementById('registerBox').style.display = 'none';
    });

    document.getElementById('loginForm').addEventListener('submit', handleLogin);
    document.getElementById('registerForm').addEventListener('submit', handleRegister);
    document.getElementById('logoutBtn').addEventListener('click', handleLogout);

    // 菜单相关
    document.getElementById('startGameBtn').addEventListener('click', startGame);
    document.getElementById('rankingsBtn').addEventListener('click', showRankings);
    document.getElementById('recordsBtn').addEventListener('click', showRecords);

    // 游戏控制
    document.getElementById('pauseBtn').addEventListener('click', togglePause);
    document.getElementById('backToMenuBtn').addEventListener('click', backToMenu);
    
    // 排行榜
    document.getElementById('globalTab').addEventListener('click', () => loadRankings('global'));
    document.getElementById('dailyTab').addEventListener('click', () => loadRankings('daily'));
    document.getElementById('backFromRankings').addEventListener('click', () => showPage('menu'));
    document.getElementById('backFromRecords').addEventListener('click', () => showPage('menu'));

    // 游戏结束
    document.getElementById('restartBtn').addEventListener('click', restartGame);
    document.getElementById('backToMenuFromOver').addEventListener('click', () => {
        document.getElementById('gameOverModal').style.display = 'none';
        showPage('menu');
    });

    // 键盘控制
    document.addEventListener('keydown', handleKeyPress);
}

// 处理登录
async function handleLogin(e) {
    e.preventDefault();
    const username = document.getElementById('loginUsername').value;
    const password = document.getElementById('loginPassword').value;

    try {
        await api.login(username, password);
        showPage('menu');
        updateUserInfo();
        alert('登录成功!');
    } catch (error) {
        alert('登录失败: ' + error.message);
    }
}

// 处理注册
async function handleRegister(e) {
    e.preventDefault();
    const username = document.getElementById('regUsername').value;
    const password = document.getElementById('regPassword').value;
    const email = document.getElementById('regEmail').value;

    try {
        await api.register(username, password, email || null);
        alert('注册成功！请登录');
        document.getElementById('showLogin').click();
    } catch (error) {
        alert('注册失败: ' + error.message);
    }
}

// 处理登出
function handleLogout() {
    api.clearToken();
    showPage('auth');
    updateUserInfo();
}

// 更新用户信息显示
function updateUserInfo() {
    const username = api.getCurrentUsername();
    document.getElementById('username').textContent = username;
    document.getElementById('logoutBtn').style.display = api.isLoggedIn() ? 'inline-block' : 'none';
}

// 显示页面
function showPage(pageName) {
    const pages = ['authPage', 'menuPage', 'gamePage', 'rankingsPage', 'recordsPage'];
    pages.forEach(page => {
        document.getElementById(page).style.display = 'none';
    });
    document.getElementById(pageName + 'Page').style.display = 'block';
    currentPage = pageName;
}

// 开始游戏
function startGame() {
    showPage('game');
    game = new SnakeGame('gameCanvas');
    game.onGameOver = handleGameOver;
    game.start();
}

// 切换暂停
function togglePause() {
    if (game) {
        const isPaused = game.togglePause();
        document.getElementById('pauseBtn').textContent = isPaused ? '继续' : '暂停';
    }
}

// 返回菜单
function backToMenu() {
    if (game) {
        if (confirm('确定要退出当前游戏吗？')) {
            game.stop();
            game = null;
            showPage('menu');
        }
    } else {
        showPage('menu');
    }
}

// 重新开始游戏
function restartGame() {
    document.getElementById('gameOverModal').style.display = 'none';
    startGame();
}

// 处理游戏结束
async function handleGameOver(gameData) {
    try {
        const result = await api.saveGameRecord(gameData);
        
        document.getElementById('finalScore').textContent = gameData.score;
        document.getElementById('finalLength').textContent = gameData.maxLength;
        document.getElementById('finalTime').textContent = gameData.gameDuration;
        document.getElementById('finalStage').textContent = gameData.maxStage;
        document.getElementById('globalRank').textContent = result.ranking;
        
        if (result.isNewHighScore) {
            document.getElementById('newRecord').style.display = 'block';
        } else {
            document.getElementById('newRecord').style.display = 'none';
        }
        
        document.getElementById('gameOverModal').style.display = 'flex';
    } catch (error) {
        console.error('保存游戏记录失败:', error);
        alert('保存游戏记录失败: ' + error.message);
    }
}

// 显示排行榜
async function showRankings() {
    showPage('rankings');
    await loadRankings('global');
}

// 加载排行榜
async function loadRankings(type) {
    try {
        const rankings = type === 'global' 
            ? await api.getGlobalRankings(10)
            : await api.getDailyRankings(10);
        
        displayRankings(rankings);
        
        // 更新标签状态
        document.getElementById('globalTab').classList.toggle('active', type === 'global');
        document.getElementById('dailyTab').classList.toggle('active', type === 'daily');
    } catch (error) {
        console.error('加载排行榜失败:', error);
        alert('加载排行榜失败');
    }
}

// 显示排行榜数据
function displayRankings(rankings) {
    const list = document.getElementById('rankingsList');
    list.innerHTML = '';
    
    if (rankings.length === 0) {
        list.innerHTML = '<p style="text-align:center;padding:20px;">暂无数据</p>';
        return;
    }
    
    rankings.forEach(item => {
        const div = document.createElement('div');
        div.className = 'ranking-item' + (item.rank <= 3 ? ' top3' : '');
        div.innerHTML = `
            <div>
                <span style="font-size:20px;margin-right:10px;">${item.rank}</span>
                <span style="font-weight:bold;">${item.username}</span>
            </div>
            <div>
                <span style="color:#667eea;">🏆 ${item.highestScore}分</span>
                <span style="margin-left:15px;color:#999;">🎮 ${item.totalGames}局</span>
            </div>
        `;
        list.appendChild(div);
    });
}

// 显示游戏记录
async function showRecords() {
    showPage('records');
    try {
        const records = await api.getUserRecords(0, 20);
        displayRecords(records);
    } catch (error) {
        console.error('加载记录失败:', error);
        alert('加载记录失败');
    }
}

// 显示记录数据
function displayRecords(records) {
    const list = document.getElementById('recordsList');
    list.innerHTML = '';
    
    if (records.length === 0) {
        list.innerHTML = '<p style="text-align:center;padding:20px;">暂无游戏记录</p>';
        return;
    }
    
    records.forEach(record => {
        const div = document.createElement('div');
        div.className = 'record-item';
        const date = new Date(record.createdAt).toLocaleString('zh-CN');
        div.innerHTML = `
            <div>
                <div style="font-weight:bold;">得分: ${record.score}</div>
                <div style="font-size:12px;color:#999;margin-top:5px;">${date}</div>
            </div>
            <div style="text-align:right;">
                <div>长度: ${record.maxLength} | 阶段: ${record.maxStage}</div>
                <div style="font-size:12px;color:#999;margin-top:5px;">
                    时长: ${record.gameDuration}秒 | 道具: ${record.itemsCollected}
                </div>
            </div>
        `;
        list.appendChild(div);
    });
}

// 处理键盘按键
function handleKeyPress(e) {
    if (currentPage !== 'game' || !game || game.isPaused) return;
    
    switch(e.key) {
        case 'ArrowUp':
            e.preventDefault();
            game.changeDirection(DIRECTIONS.UP);
            break;
        case 'ArrowDown':
            e.preventDefault();
            game.changeDirection(DIRECTIONS.DOWN);
            break;
        case 'ArrowLeft':
            e.preventDefault();
            game.changeDirection(DIRECTIONS.LEFT);
            break;
        case 'ArrowRight':
            e.preventDefault();
            game.changeDirection(DIRECTIONS.RIGHT);
            break;
        case ' ':
            e.preventDefault();
            togglePause();
            break;
    }
}
