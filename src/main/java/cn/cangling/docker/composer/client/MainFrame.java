package cn.cangling.docker.composer.client;

import cn.cangling.docker.composer.client.composer.ComposerFrame;
import cn.cangling.docker.composer.client.js.DateTimes;
import cn.cangling.docker.composer.client.resource.ComposerResource;
import cn.cangling.docker.composer.client.version.CompileInfo;
import cn.cangling.docker.composer.client.version.ICompileInfoProvider;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;


public class MainFrame extends Composite implements RequiresResize {
    private static final MainFrameUiBinder ourUiBinder = GWT.create(MainFrameUiBinder.class);
    @UiField
    HTML logo;
    @UiField
    ComposerFrame composerFrame;
    @UiField
    DockLayoutPanel root;
    @UiField
    Label lbName;
    @UiField
    Label lbVersion;

    public MainFrame() {
        initWidget(ourUiBinder.createAndBindUi(this));
        logo.setHTML(ComposerResource.INSTANCE.dockerHelper().getText());
        ICompileInfoProvider provider=GWT.create(ICompileInfoProvider.class);
        CompileInfo compileInfo = provider.getCompileInfo();
        String versionInfo=compileInfo.gitCommit;
        versionInfo+=" @"+DateTimes.formatDateTime(compileInfo.compileTime);
        lbVersion.setText(versionInfo);
    }

    @Override
    public void onResize() {
        root.onResize();
    }

    interface MainFrameUiBinder extends UiBinder<DockLayoutPanel, MainFrame> {
    }

}