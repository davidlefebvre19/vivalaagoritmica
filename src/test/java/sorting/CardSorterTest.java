package sorting;

import org.javagrader.Grade;
import org.javagrader.CustomGradingResult;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.util.*;

@Grade
public class CardSorterTest {


    @Test
    public void testExample() {
        LinkedListImpl l = new LinkedListImpl(new int[]{3,1,2,4});
        CardSorter.sort(l);
        assertTrue(l.isSorted());
    }

}


