package com.pandora.imgoptimizer.extension

import com.pandora.imgoptimizer.optimizer.Constants


class ImgOptimizerExtension {
    /**
     * 优化后生成的图片名后缀
     */
    String suffix = ''

    /**
     * 触发优化的起始大小(kb)
     */
    int triggerSize = 0

    /**
     * 压缩模式(默认有损压缩)
     */
    String type = Constants.LOSSY
}
