package ee.shanel.ideabucket.generator;

import lombok.RequiredArgsConstructor;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

@RequiredArgsConstructor
public class TestIdGenerator implements IdGenerator
{
    private static final Deque<String> IDS = new ArrayDeque<>();

    @Override
    public String generate()
    {
        return IDS.pop();
    }

    /**
     * Add all the IDs to the queue.
     *
     * @param ids the IDs
     */
    public void addAll(final Collection<String> ids)
    {
        IDS.addAll(ids);
    }

    /**
     * Clear all the IDs from the queue
     */
    public void clear()
    {
        IDS.clear();
    }

}
