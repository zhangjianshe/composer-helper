package cn.cangling.docker.composer.client.composer.model;

import elemental2.core.JsArray;
import jsinterop.annotations.JsType;

@JsType
public class HealthChecker {
    public JsArray<Service> test;
    public String interval;
    public String timeout;
    public Double retries;
    public String start_period;
}
