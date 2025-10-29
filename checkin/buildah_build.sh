#!/bin/bash

# ========================================
# 用户签到系统 Buildah 镜像构建脚本
# ========================================

set -e

# 配置变量
IMAGE_NAME="checkin-system"
IMAGE_TAG="latest"
REGISTRY="your-registry.com"  # 修改为您的镜像仓库地址
IMAGE_FULL_NAME="${REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}"

echo "========================================"
echo "=== Buildah 构建 ==="
echo "========================================"
echo "镜像名称: ${IMAGE_FULL_NAME}"
echo ""

# 检查 buildah 是否安装
if ! command -v buildah &> /dev/null; then
    echo "❌ 错误: 未找到 buildah，请先安装"
    exit 1
fi

# 获取脚本所在目录
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd "${SCRIPT_DIR}"

# 检查 Dockerfile 是否存在
if [ ! -f "Dockerfile" ]; then
    echo "❌ 错误: 未找到 Dockerfile"
    exit 1
fi

echo "当前工作目录: $(pwd)"
echo "构建上下文内容:"
ls -la
echo ""

# 检查关键文件
if [ ! -f "pom.xml" ]; then
    echo "❌ 错误: 未找到 pom.xml 文件"
    echo "请确保在 checkin 目录下执行此脚本"
    exit 1
fi

if [ ! -d "src" ]; then
    echo "❌ 错误: 未找到 src 目录"
    exit 1
fi

echo "✅ 构建文件检查通过"
echo ""

# 配置镜像仓库推送认证
echo "配置镜像仓库推送认证..."
if [ -f "$HOME/.docker/config.json" ]; then
    echo "✅ Docker配置文件认证完成"
else
    echo "⚠️  未找到 Docker 认证配置，如需推送请先登录:"
    echo "   podman login ${REGISTRY}"
fi

# 执行 buildah 构建命令
echo "执行 buildah 构建命令..."
buildah bud \
    --format docker \
    --layers \
    -t "${IMAGE_FULL_NAME}" \
    -f Dockerfile \
    .

# 检查构建结果
if [ $? -eq 0 ]; then
    echo ""
    echo "========================================"
    echo "✅ 镜像构建成功"
    echo "========================================"
    echo "镜像: ${IMAGE_FULL_NAME}"
    echo ""
    echo "后续操作:"
    echo "1. 推送镜像: buildah push ${IMAGE_FULL_NAME}"
    echo "2. 运行容器: podman run -d -p 8081:8081 --name checkin ${IMAGE_FULL_NAME}"
    echo "3. 查看镜像: buildah images"
    echo "========================================"
else
    echo ""
    echo "❌ 镜像构建失败"
    exit 1
fi

# 可选：自动推送
read -p "是否推送镜像到仓库? (y/n) " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    echo "推送镜像到仓库..."
    buildah push "${IMAGE_FULL_NAME}"
    if [ $? -eq 0 ]; then
        echo "✅ 镜像推送成功"
    else
        echo "❌ 镜像推送失败"
        exit 1
    fi
fi
