package cn.onb.tr.support;

/**
 * @Description: (onb)->所有新定义的接口需要基础Base
 * @Author: 、心
 * @Date: 19/12/21 17:10
 */
public interface JSONViewInterface {
    interface Base {
    }

    interface User {
        interface Simple extends Base {
        }

        interface All extends Simple {
        }
    }
}
