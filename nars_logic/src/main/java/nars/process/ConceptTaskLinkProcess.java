package nars.process;

import nars.concept.Concept;
import nars.link.TaskLink;
import nars.link.TermLink;

/**
 * Created by me on 8/5/15.
 */
public class ConceptTaskLinkProcess extends ConceptProcess {

    public ConceptTaskLinkProcess(Concept concept, TaskLink taskLink) {
        super(concept, taskLink);
    }

    @Override
    protected void derive() {

        getMemory().rules.fire(this);
    }

    @Override
    public TermLink getTermLink() {
        return null;
    }
}