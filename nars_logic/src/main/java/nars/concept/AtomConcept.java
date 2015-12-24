package nars.concept;

import nars.NAR;
import nars.bag.Bag;
import nars.budget.Budget;
import nars.concept.util.BeliefTable;
import nars.concept.util.TaskTable;
import nars.task.Task;
import nars.term.Term;
import nars.term.Termed;

/**
 * Created by me on 9/2/15.
 */
public class AtomConcept extends AbstractConcept  {

    protected final Bag<Task> taskLinks;
    protected final Bag<Termed> termLinks;


//    /** creates with no termlink and tasklink ability */
//    public AtomConcept(Term atom, Budget budget) {
//        this(atom, budget, new NullBag(), new NullBag());
//    }

    public AtomConcept(Term atom, Bag<Termed> termLinks, Bag<Task> taskLinks) {
        super(atom);
        this.termLinks = termLinks;
        this.taskLinks = taskLinks;
    }



    /**
     * Task links for indirect processing
     */
    @Override
    public final Bag<Task> getTaskLinks() {
        return taskLinks;
    }

    /**
     * Term links between the term and its components and compounds; beliefs
     */
    @Override
    public final Bag<Termed> getTermLinks() {
        return termLinks;
    }


    @Override
    public BeliefTable getBeliefs() {
        return BeliefTable.EMPTY;
    }

    @Override
    public BeliefTable getGoals() {
        return BeliefTable.EMPTY;
    }

    @Override
    public TaskTable getQuestions() {
        return BeliefTable.EMPTY;
    }

    @Override
    public TaskTable getQuests() {
        return BeliefTable.EMPTY;
    }

    static final String shouldntProcess = "should not have attempted to process task here";

    @Override
    public boolean processBelief(Task task, NAR nar) {
        throw new RuntimeException(shouldntProcess);
    }
    @Override
    public boolean processGoal(Task task, NAR nar) {
        throw new RuntimeException(shouldntProcess);
    }
    @Override
    public boolean processQuestion(Task task, NAR nar) {
        throw new RuntimeException(shouldntProcess);
    }
    @Override
    public final boolean processQuest(Task task, NAR nar) {
        return processQuestion(task, nar );
    }


    /** atoms have no termlink templates, they are irreducible */
    @Override public Termed[] getTermLinkTemplates() {
        return null;
    }

    /**
     * when a task is processed, a tasklink
     * can be created at the concept of its term
     */
    @Override public final boolean link(Task t, float scale, float minScale, NAR nar) {

        Termed[] templates = getTermLinkTemplates();
        if (templates == null) return false;

        int numTemplates = templates.length;
        if (numTemplates == 0) return false;

        //activate tasklink locally
        getTaskLinks().put(t, t.getBudget(), scale);

        float subScale = scale / numTemplates;
        if (subScale < minScale)
            return false;

        for (Termed linkTemplate : templates) {

            Concept componentConcept = nar.conceptualize(linkTemplate);

            if (componentConcept != null) {

                /** activate the peer task tlink */
                componentConcept.link(t, subScale, minScale, nar);
            }
        }

        linkTemplates(t.getBudget(), scale, nar);

        return true;
    }

    /** atom concept, being irreducible, will have no templates to recurse into */
    @Override public void linkTemplates(Budget budget, float scale, NAR nar) {
    }

    @Override
    public boolean process(Task task, NAR nar) {
        return false;
    }
}
