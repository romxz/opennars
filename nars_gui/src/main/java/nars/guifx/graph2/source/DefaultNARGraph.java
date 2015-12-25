package nars.guifx.graph2.source;

import javafx.beans.InvalidationListener;
import nars.guifx.annotation.Implementation;
import nars.guifx.annotation.ImplementationProperty;
import nars.guifx.demo.POJOPane;
import nars.guifx.graph2.ConceptsSource;
import nars.guifx.graph2.GraphSource;
import nars.guifx.graph2.TermNode;
import nars.guifx.graph2.VisModel;
import nars.guifx.graph2.impl.CanvasEdgeRenderer;
import nars.guifx.graph2.layout.*;
import nars.guifx.graph2.layout.FastOrganicLayout;
import nars.guifx.graph2.scene.DefaultVis;
import nars.term.Termed;

import static javafx.application.Platform.runLater;

/**
 * provides defalut settings for a NARGraph view
 */
public class DefaultNARGraph<K extends Comparable & Termed, V extends TermNode<K>> extends SpaceGrapher<K,V> {

    @Implementation(FastOrganicLayout.class)
    @Implementation(HyperOrganicLayout.class)
    @Implementation(HyperassociativeMap2D.class)
    @Implementation(Spiral.class)
    @Implementation(Circle.class)
    @Implementation(Grid.class)
    @Implementation(HyperassociativeMap1D.class)
    @Implementation(Hilbert.class)
    //@Implementation(TimeGraph.class)
    public final ImplementationProperty<IterativeLayout> layoutType = new ImplementationProperty();


    public DefaultNARGraph(int capacity, ConceptsSource source) {
        this(
                source, capacity, new DefaultVis(),
                new CanvasEdgeRenderer());
    }

    public DefaultNARGraph(GraphSource source, int size, VisModel v, CanvasEdgeRenderer edgeRenderer) {

        super(source, v, edgeRenderer, size);

        InvalidationListener layoutChange = e -> {
            IterativeLayout il = layoutType.getInstance();
            if (il!=null) {
                layout.set(il);
                layoutUpdated();
            } else {
                IterativeLayout il2 = new HyperOrganicLayout<>(); 
                layout.set(il2);
                layoutUpdated();
            }
        };

        layoutType.addListener(layoutChange);

        layoutType.setValue(HyperOrganicLayout.class);

        runLater(() -> layoutChange.invalidated(null));

        POJOPane c = new POJOPane(this);
        c.layout();
        c.autosize();
        getChildren().add(c);


    }

}
