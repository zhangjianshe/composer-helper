package cn.cangling.docker.composer.client.composer.model;

/**
 * 点击测试结果
 */
public class HitTestResult {
    private HitTest hitTest;
    private Object data;
    public HitTestResult(HitTest hitTest, Object data) {
        this.hitTest = hitTest;
        this.data = data;
    }
    public HitTest getHitTest() {
        return hitTest;
    }
    public <T> T getData(){
        return (T)data;
    }
    public static HitTestResult create(HitTest hitTest, Object data) {
        return new HitTestResult(hitTest,data);
    }
}
