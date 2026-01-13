// API配置
const API_BASE_URL = 'http://localhost:8080/api';

// 本地存储键
const STORAGE_KEYS = {
    TOKEN: 'snake_game_token',
    USER_ID: 'snake_game_user_id',
    USERNAME: 'snake_game_username'
};

// API客户端类
class ApiClient {
    constructor() {
        this.baseURL = API_BASE_URL;
    }

    // 获取Token
    getToken() {
        return localStorage.getItem(STORAGE_KEYS.TOKEN);
    }

    // 设置Token
    setToken(token) {
        localStorage.setItem(STORAGE_KEYS.TOKEN, token);
    }

    // 清除Token
    clearToken() {
        localStorage.removeItem(STORAGE_KEYS.TOKEN);
        localStorage.removeItem(STORAGE_KEYS.USER_ID);
        localStorage.removeItem(STORAGE_KEYS.USERNAME);
    }

    // 通用请求方法
    async request(endpoint, options = {}) {
        const url = `${this.baseURL}${endpoint}`;
        const headers = {
            'Content-Type': 'application/json',
            ...options.headers
        };

        const token = this.getToken();
        if (token && !endpoint.includes('/auth/')) {
            headers['Authorization'] = `Bearer ${token}`;
        }

        try {
            const response = await fetch(url, {
                ...options,
                headers
            });

            const data = await response.json();
            
            if (data.code !== 200) {
                throw new Error(data.message || '请求失败');
            }

            return data.data;
        } catch (error) {
            console.error('API请求错误:', error);
            throw error;
        }
    }

    // 用户注册
    async register(username, password, email) {
        return await this.request('/auth/register', {
            method: 'POST',
            body: JSON.stringify({ username, password, email })
        });
    }

    // 用户登录
    async login(username, password) {
        const data = await this.request('/auth/login', {
            method: 'POST',
            body: JSON.stringify({ username, password })
        });
        
        if (data.token) {
            this.setToken(data.token);
            localStorage.setItem(STORAGE_KEYS.USER_ID, data.userId);
            localStorage.setItem(STORAGE_KEYS.USERNAME, data.username);
        }
        
        return data;
    }

    // 保存游戏记录
    async saveGameRecord(recordData) {
        return await this.request('/game/record', {
            method: 'POST',
            body: JSON.stringify(recordData)
        });
    }

    // 获取用户游戏记录
    async getUserRecords(page = 0, size = 10) {
        return await this.request(`/game/records?page=${page}&size=${size}`);
    }

    // 获取全局排行榜
    async getGlobalRankings(limit = 10) {
        return await this.request(`/ranking/global?limit=${limit}`);
    }

    // 获取每日排行榜
    async getDailyRankings(limit = 10) {
        return await this.request(`/ranking/daily?limit=${limit}`);
    }

    // 获取道具配置
    async getItemConfigs() {
        return await this.request('/game/items');
    }

    // 获取用户信息
    async getUserProfile() {
        return await this.request('/user/profile');
    }

    // 检查是否已登录
    isLoggedIn() {
        return !!this.getToken();
    }

    // 获取当前用户名
    getCurrentUsername() {
        return localStorage.getItem(STORAGE_KEYS.USERNAME) || '未登录';
    }
}

// 导出API客户端实例
const api = new ApiClient();
