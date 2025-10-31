#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from reportlab.lib.pagesizes import letter
from reportlab.pdfgen import canvas
from reportlab.pdfbase import pdfmetrics
from reportlab.pdfbase.ttfonts import TTFont
import os

def create_poem_pdf():
    """创建包含《登高》诗内容的PDF文件"""
    
    # 确保目标目录存在
    output_path = "/Users/issuser/Documents/file-test/sample.pdf"
    output_dir = os.path.dirname(output_path)
    if not os.path.exists(output_dir):
        os.makedirs(output_dir)
    
    # 创建PDF
    c = canvas.Canvas(output_path, pagesize=letter)
    width, height = letter
    
    # 尝试使用系统中文字体
    try:
        # macOS 系统常见中文字体
        font_paths = [
            '/System/Library/Fonts/STHeiti Light.ttc',
            '/System/Library/Fonts/PingFang.ttc',
            '/Library/Fonts/Arial Unicode.ttf',
        ]
        
        font_registered = False
        for font_path in font_paths:
            if os.path.exists(font_path):
                try:
                    pdfmetrics.registerFont(TTFont('Chinese', font_path))
                    c.setFont('Chinese', 16)
                    font_registered = True
                    break
                except:
                    continue
        
        if not font_registered:
            # 如果没有找到字体，使用默认字体
            c.setFont('Helvetica', 12)
            print("警告：未找到中文字体，使用默认字体")
    except Exception as e:
        c.setFont('Helvetica', 12)
        print(f"字体加载错误：{e}")
    
    # 设置标题
    c.setFont('Chinese', 24) if font_registered else c.setFont('Helvetica-Bold', 20)
    c.drawCentredString(width / 2, height - 100, "登高")
    
    # 设置作者
    c.setFont('Chinese', 14) if font_registered else c.setFont('Helvetica', 12)
    c.drawCentredString(width / 2, height - 140, "唐·杜甫")
    
    # 诗歌内容
    poem_lines = [
        "风急天高猿啸哀，渚清沙白鸟飞回。",
        "无边落木萧萧下，不尽长江滚滚来。",
        "万里悲秋常作客，百年多病独登台。",
        "艰难苦恨繁霜鬓，潦倒新停浊酒杯。"
    ]
    
    # 绘制诗句
    y_position = height - 200
    c.setFont('Chinese', 16) if font_registered else c.setFont('Helvetica', 12)
    
    for line in poem_lines:
        c.drawCentredString(width / 2, y_position, line)
        y_position -= 40
    
    # 保存PDF
    c.save()
    print(f"PDF文件已成功创建：{output_path}")

if __name__ == "__main__":
    create_poem_pdf()
