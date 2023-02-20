package com.zagvladimir.service.tail;

import com.zagvladimir.dao.TailDAO;
import com.zagvladimir.model.Tail;
import com.zagvladimir.model.enums.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TailServiceImplTest {

    @Mock
    private TailDAO tailDAO;

    @InjectMocks
    private TailServiceImpl tailService;

    private final Tail TAIL_CAT;
    private final Tail TAIL_DOG;
    private final Tail TAILKITTEN;

    private TailServiceImplTest() {
        TAIL_CAT = new Tail();
        TAIL_DOG = new Tail();
        TAILKITTEN = new Tail();

        TAIL_CAT.setType("CAT");
        TAIL_DOG.setType("DOG");
        TAILKITTEN.setType("KITTEN");

        TAIL_CAT.setStatus(Status.ACTIVE);
        TAIL_DOG.setStatus(Status.ACTIVE);
        TAILKITTEN.setStatus(Status.DELETED);

    }

    @Test
    void findAll() {
        List<Tail> expectedTails = new ArrayList<>();
        expectedTails.add(TAIL_CAT);
        expectedTails.add(TAIL_DOG);
        expectedTails.add(TAILKITTEN);

        when(tailDAO.findAll()).thenReturn(expectedTails);

        List<Tail> actualTails = tailService.findAll();

        verify(tailDAO, times(1)).findAll();
        assertEquals(expectedTails, actualTails);
    }

    @Test
    void findById() {
        Tail expectedTail = new Tail();
        expectedTail.setType("CAT");

        when(tailDAO.findById(any(Integer.class))).thenReturn(Optional.of(expectedTail));

        Tail actualTail = tailService.findById(2);
        assertEquals(expectedTail, actualTail);
    }

    @Test
    void findAllWithStatusActive() {
        List<Tail> tails = new ArrayList<>();
        tails.add(TAIL_CAT);
        tails.add(TAIL_DOG);
        tails.add(TAILKITTEN);

        when(tailDAO.findAll()).thenReturn(tails);

        List<Tail> activeTails = tailService.findAllWithStatusActive();

        verify(tailDAO, times(1)).findAll();

        List<Tail> expectedTails = new ArrayList<>();
        expectedTails.add(TAIL_CAT);
        expectedTails.add(TAIL_DOG);
        assertEquals(expectedTails, activeTails);
    }

    @Test
    void create() {
        Tail createdTail = new Tail();
        createdTail.setType("CAT");
        createdTail.setId(2);
        Integer expectedId = 2;

        when(tailDAO.create(any(Tail.class))).thenReturn(createdTail);

        Integer actualTailId = tailService.create(createdTail);
        assertEquals(expectedId, actualTailId);
    }

    @Test
    void softDeleteTailById() {
        Tail updatedTail = new Tail();
        updatedTail.setType("CAT");
        updatedTail.setId(2);

        when(tailDAO.findById(any(Integer.class))).thenReturn(Optional.of(updatedTail));
        when(tailDAO.update(updatedTail)).thenReturn(updatedTail);

        tailService.softDeleteTailById(2);

        verify(tailDAO, Mockito.times(1)).findById(2);
        verify(tailDAO, Mockito.times(1)).update(updatedTail);
    }

    @Test
    void deleteById() {
        Integer tailId = 2;
        tailService.deleteById(tailId);

        verify(tailDAO, Mockito.times(1)).delete(tailId);
    }
}