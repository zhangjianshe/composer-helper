package cn.cangling.docker.composer.client;

import cn.cangling.docker.composer.client.composer.ComposerFrame;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;

public class Composerui implements EntryPoint {
    public void onModuleLoad() {
        RootLayoutPanel root = RootLayoutPanel.get();
        root.add(new MainFrame());
    }
}
