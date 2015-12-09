package nars.nal.nal8;

import nars.Global;
import nars.Narsese;
import nars.Op;
import nars.task.Task;
import nars.term.Term;
import nars.term.transform.FindSubst;
import nars.term.transform.Subst;
import nars.util.data.random.XorShift1024StarRandom;

import java.util.List;
import java.util.function.Function;


public abstract class PatternOperation implements Function<Task, List<Task>> {

    final XorShift1024StarRandom rng = new XorShift1024StarRandom(1);
    public final Term pattern;

    public PatternOperation(String pattern) {
        this.pattern = Narsese.the().termRaw(pattern);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + pattern.toString() + "]";
    }

    @Override
    public List<Task> apply(Task operationTask) {

        if (operationTask.isGoal()) {
            FindSubst s = new FindSubst(Op.VAR_PATTERN, rng);
            if (s.next(pattern, operationTask.getTerm(), Global.UNIFICATION_POWER)) {
                return run(operationTask, s);
            }
        }

        return null;
    }

    public abstract List<Task> run(Task operationTask, Subst map1);
}
