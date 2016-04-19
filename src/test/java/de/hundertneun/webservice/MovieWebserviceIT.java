package de.hundertneun.webservice;

import de.hundertneun.repository.MockRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.Test;

import javax.ws.rs.NotFoundException;

public class MovieWebserviceIT extends BaseIT {

    
    @Test
    public void allMovies() {
        String jsonRespone = requestForPath("movies");
        
        //check if all titles exit
        assertThat(jsonRespone).contains(
                MockRepository.MOVIE1.getTitle(),
                MockRepository.MOVIE2.getTitle(),
                MockRepository.MOVIE3.getTitle(),
                MockRepository.MOVIE4.getTitle()
        );
    }

    @Test
    public void shows_validMovieName_returnsAllMovieShows() throws Exception {
        String jsonRespone = requestForPath("shows/" + MockRepository.MOVIE4.getTitle());
        
        //check if result contains at leat one date in the correct format
        assertThat(jsonRespone).matches(".*\"\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}\".*");
    }

    @Test
    public void shows_invalidMovieName_returnsNOT_FOUND() throws Exception {
        assertThatThrownBy(() -> requestForPath("shows/nonexisting")).isInstanceOf(NotFoundException.class);
    }


    private String requestForPath(String movies) {
        String jsonRespone = target.path(movies).request().get(String.class);
        System.out.println(jsonRespone);
        return jsonRespone;
    }
}