package cn.cangling.docker.composer.client.js;

import elemental2.core.Function;
import elemental2.core.JsObject;
import jsinterop.base.Js;

public class Jss {
    public Jss() {
    }

    public static <T> T castTo(Object instance, Class<T> clazz) {
        if(instance==null)return null;
        T prototypeInstance = (T) Js.uncheckedCast(JsObject.getPrototypeOf(Js.asConstructorFn(clazz).construct(new Object[0])));
        return (T)Js.uncheckedCast(JsObject.setPrototypeOf(instance, prototypeInstance));
    }

    public static Function castToFunction(Object instance) {
        return instance != null && Js.typeof(instance) == "function" ? (Function)Js.uncheckedCast(instance) : null;
    }
}
