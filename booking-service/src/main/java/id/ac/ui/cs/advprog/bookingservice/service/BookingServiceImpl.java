package id.ac.ui.cs.advprog.bookingservice.service;

import id.ac.ui.cs.advprog.bookingservice.model.Booking;
import id.ac.ui.cs.advprog.bookingservice.model.dto.CreateBookingDTO;
import id.ac.ui.cs.advprog.bookingservice.repository.BookingRepository;
import id.ac.ui.cs.advprog.bookingservice.vo.Asdos;
import id.ac.ui.cs.advprog.bookingservice.vo.BookingApproval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Booking> getAllBooking() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking createBooking(CreateBookingDTO bookingRequest) {
        BookingApproval bookingResponse =
                restTemplate.postForObject("http://ASDOS-SERVICE/asdos/book", bookingRequest, BookingApproval.class);

        // Print as log to make use of the message
        System.out.println(bookingResponse.getMessage());

        if (bookingResponse.getStatus().equals("Success")) {
            Booking newBooking = new Booking();

            newBooking.setBookerName(bookingRequest.getName());
            newBooking.setBookerClass(bookingRequest.getBookerClass());
            newBooking.setAsdosId(bookingRequest.getAsdosCode());
            newBooking.setBookTime(bookingRequest.getRequestedTime());

            bookingRepository.save(newBooking);
            return newBooking;
        }
        return null;
    }

    @Override
    public Booking deleteBooking(int id) {
        Booking booking = bookingRepository.findBookingById(id);
        bookingRepository.deleteById(booking.getId());
        return booking;
    }

    @Override
    public List<Asdos> getAllAsdos() {
        ResponseEntity<Asdos[]> asdosResponse = restTemplate.getForEntity("http://ASDOS-SERVICE/asdos/", Asdos[].class);
        List<Asdos> asdosList = Arrays.asList(asdosResponse.getBody());
        return asdosList;
    }
}
