package cn.cangling.docker.composer.client.composer.editor;

import cn.cangling.docker.composer.client.composer.model.*;
import cn.cangling.docker.composer.client.composer.template.ObjectTemplate;
import cn.cangling.docker.composer.client.composer.template.ObjectTemplates;
import cn.cangling.docker.composer.client.resource.ComposerResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.Widget;
import elemental2.dom.*;
import jsinterop.base.Js;

/**
 * composer editor
 * output a yaml
 */
public class ComposerEditor extends Widget implements RequiresResize {
    HTMLCanvasElement canvas;
    ImageResource background;
    int FPS = 60;
    double scale = 1;
    boolean continueDrawing = true;
    CanvasPattern backgroundPattern;
    double lastTime = 0;
    ServiceYaml serviceYaml = new ServiceYaml();
    YamlGraph graph = null;
    DefaultMouseAction defaultMouseAction;
    IMouseAction currentMouseAction =null;
    public ComposerEditor() {
        canvas = Js.uncheckedCast(DomGlobal.document.createElement("canvas"));
        setElement(Js.uncheckedCast(canvas));
        canvas.oncontextmenu = p0 -> false;
        background = ComposerResource.INSTANCE.grid();
        defaultMouseAction = new DefaultMouseAction();
        installEventHandlers();
    }

    /**
     * 处理交互事件
     */
    private void installEventHandlers() {

        canvas.addEventListener("dragover", event -> {
            DragEvent dragEvent = Js.uncheckedCast(event);
            dragEvent.preventDefault();
            dragEvent.stopPropagation();
            if (dragEvent.dataTransfer.types.includes(ObjectTemplates.DATA_TYPE)) {
                dragEvent.dataTransfer.dropEffect = "copy";
            } else {
                dragEvent.dataTransfer.dropEffect = "none";
            }
        });

        canvas.addEventListener("drop", event -> {
            DragEvent dragEvent = Js.uncheckedCast(event);
            dragEvent.preventDefault();
            dragEvent.stopPropagation();
            if (dragEvent.dataTransfer.types.includes(ObjectTemplates.DATA_TYPE)) {
                MouseEvent mouseEvent = Js.uncheckedCast(event);
                String name = dragEvent.dataTransfer.getData(ObjectTemplates.DATA_TYPE);
                DOMRect containerRect = canvas.getBoundingClientRect();
                double xInContainer = mouseEvent.clientX - containerRect.left;
                double yInContainer = mouseEvent.clientY - containerRect.top;
                addInstanceFromTemplate(xInContainer, yInContainer, name);
            }
        });

        canvas.onmousedown = p0 -> {
            MouseEvent mouseEvent = Js.uncheckedCast(p0);
            if(currentMouseAction ==null) {
                defaultMouseAction.setData(graph,canvas);
                currentMouseAction =defaultMouseAction;
            }
            IMouseAction nextAction= currentMouseAction.onDown(mouseEvent);
            if(nextAction!=null)
            {
                currentMouseAction=nextAction;
                currentMouseAction.onDown(mouseEvent);
            }
            return true;
        };

        canvas.onmousemove = p0 -> {
            if (currentMouseAction !=null) {
                MouseEvent mouseEvent = Js.uncheckedCast(p0);
                currentMouseAction.onMove(mouseEvent);
            }
            return true;
        };

        canvas.onmouseup = p0 -> {
            if (currentMouseAction !=null) {
                MouseEvent mouseEvent = Js.uncheckedCast(p0);
                currentMouseAction.onUp(mouseEvent);
            }
            currentMouseAction =null;
            return true;
        };
    }

    private void addInstanceFromTemplate(double xInContainer, double yInContainer, String name) {
        ObjectTemplate byName = ObjectTemplates.get().findByName(name);
        if (byName == null) {
            return;
        }
        switch (byName.kind) {
            case SERVICE:
                Service service = new Service(name);
                service.image = byName.image.src;
                service.name = graph.nextServiceName(name);
                ServiceObject serviceObject = graph.addService(service);
                serviceObject.rect(xInContainer - 50, yInContainer - 50, 100, 100);
                serviceObject.layout();
                break;
            case NETWORK:
                Network network = new Network(name);
                network.name = graph.nextNetworkName(name);
                NetworkObject networkObject = graph.addNetwork(network);
                networkObject.rect(xInContainer - 50, yInContainer - 50, 100, 100);
                networkObject.layout();
                break;
            case VOLUME:
                Volume volume = new Volume(name);
                // 计算名称 保证不会重复
                volume.name = graph.nextVolumeName(name);
                VolumeObject volumeObject = graph.addVolume(volume);
                volumeObject.rect(xInContainer - 50, yInContainer - 50, 100, 100);
                volumeObject.layout();
                break;
        }
    }




    @Override
    public void onResize() {
        int width = getParent().getOffsetWidth();
        int height = getParent().getOffsetHeight();
        double ratio = DomGlobal.window.devicePixelRatio;
        canvas.width = (int) (width * ratio);
        canvas.height = (int) (height * ratio);
        canvas.style.width = CSSProperties.WidthUnionType.of(width + "px");
        canvas.style.height = CSSProperties.HeightUnionType.of(height + "px");
        scale = ratio;
        CanvasRenderingContext2D context = Js.uncheckedCast(canvas.getContext("2d"));
        context.scale(ratio, ratio);
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        if (graph == null) {
           graph=new YamlGraph();
        }
        startDrawing();
    }

    private void startDrawing() {
        DomGlobal.requestAnimationFrame(this::onDraw);
    }

    @Override
    protected void onUnload() {
        super.onUnload();
        endDrawing();
    }

    private void endDrawing() {
        continueDrawing = false;
    }

    void onDraw(double t) {
        //控制刷屏速率
        if (lastTime <= 0) {
            this.lastTime = t;
        } else {
            if (t - lastTime < (double) 1000 / FPS) {
                DomGlobal.requestAnimationFrame(this::onDraw);
                return;
            }
            lastTime = t;
        }
        //绘制背景
        CanvasRenderingContext2D context = Js.uncheckedCast(canvas.getContext("2d"));
        drawBackground(context);

        if (graph != null) {
            graph.draw(context, t);
        }
        if (continueDrawing) {
            DomGlobal.requestAnimationFrame(this::onDraw);
        }
    }

    /**
     * 绘制背景
     *
     * @param context
     */
    private void drawBackground(CanvasRenderingContext2D context) {
        if (backgroundPattern == null) {
            Image image = new Image(background);

            HTMLImageElement imageElement = Js.uncheckedCast(image.getElement());
            backgroundPattern = context.createPattern(imageElement, "repeat");
        }
        context.rect(0, 0, canvas.width, canvas.height);
        context.fillStyle = BaseRenderingContext2D.FillStyleUnionType.of(backgroundPattern);
        context.fill();
    }

    public YamlGraph getYamlGraph() {
        return graph;
    }
}
