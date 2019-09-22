package com.pandora.imgoptimizer.optimizer

import com.pandora.imgoptimizer.optimizer.impl.PngquantOptimizer
import com.pandora.imgoptimizer.optimizer.impl.ZopflipngOptimizer;


class OptimizerFactory {

    private OptimizerFactory() {}

    static Optimizer getOptimizer(String type) {
        if (Constants.LOSSY == type) {
            return new PngquantOptimizer()
        } else if (Constants.LOSSLESS == type) {
            return new ZopflipngOptimizer()
        } else {
            throw new IllegalArgumentException("Unacceptable optimizer type. Please use lossy or lossless.")
        }
    }
}