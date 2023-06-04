package com.bnl.bloodbank;

import com.bnl.bloodbank.entity.Request;
import com.bnl.bloodbank.exception.NotPresentException;
import com.bnl.bloodbank.repository.RequestRepository;
import com.bnl.bloodbank.service.RequestService;
import com.bnl.bloodbank.service.RequestServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class RequestTest {

    @Mock
    RequestRepository requestRepository;

    @InjectMocks
    RequestService requestService = new RequestServiceImpl();

    private static Request request =
            Request.builder()
                    .requestId(1)
                    .bloodGroup("A+")
                    .quantity(2)
                    .build();

    /**
     * To check addRequest is successful
     */
    @Test
    void validAddReqeust() {
        Assertions.assertEquals(requestService.addRequest(request), "Request saved successfully");
    }

    /**
     * To check update status of request is successful if requestId is valid
     * @throws NotPresentException
     */
    @Test
    void validUpdateStatus() throws NotPresentException{
        Mockito.when(requestRepository.findById(request.getRequestId())).thenReturn(Optional.of(request));
        Assertions.assertEquals(requestService.updateStatus(request.getRequestId(), request.getStatus()), "Status for Request ID " + request.getRequestId() + " successfully updated");
    }

    /**
     * To check updateStatus throws NotPresentException if requestId is not valid
     * @throws NotPresentException
     */
    @Test
    void invalidUpdateStatus() throws NotPresentException{
        Mockito.when(requestRepository.findById(request.getRequestId())).thenReturn(Optional.empty());
        NotPresentException ex = Assertions.assertThrows(
                NotPresentException.class,
                () -> requestService.updateStatus(request.getRequestId() ,request.getStatus())
        );
        Assertions.assertEquals(ex.getMessage(), "Request for id " + request.getRequestId() + " not found");
    }

    /**
     * to check deleteRequest is successful if requestId is valid
     * @throws NotPresentException
     */
    @Test
    void validDeleteRequest() throws NotPresentException{
        Mockito.when(requestRepository.findById(request.getRequestId())).thenReturn(Optional.of(request));
        Assertions.assertEquals(requestService.deleteRequest(request.getRequestId()), "Request with ID " + request.getRequestId() + " successfully deleted");
    }

    /**
     * To check deleteRequest throws NotPresentException if requestId is not valid
     * @throws NotPresentException
     */
    @Test
    void invalidDeleteRequest() throws NotPresentException{
        Mockito.when(requestRepository.findById(request.getRequestId())).thenReturn(Optional.empty());
        NotPresentException ex = Assertions.assertThrows(
                NotPresentException.class,
                () -> requestService.deleteRequest(request.getRequestId())
        );
        Assertions.assertEquals(ex.getMessage(), "Request for id " + request.getRequestId() + " not found");
    }
}
