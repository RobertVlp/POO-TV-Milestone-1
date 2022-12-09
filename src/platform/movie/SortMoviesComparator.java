package platform.movie;

import java.util.Comparator;

public class SortMoviesComparator implements Comparator<Movie> {
    private final String duration;
    private final String rating;

    public SortMoviesComparator(final String duration, final String rating) {
        this.duration = duration;
        this.rating = rating;
    }

    @Override
    public int compare(Movie o1, Movie o2) {
        if (duration != null && rating != null) {
            if (o1.getDuration().equals(o2.getDuration())) {
                if (rating.equals("decreasing")) {
                    return o2.getRating().compareTo(o1.getRating());
                } else {
                    return o1.getRating().compareTo(o2.getRating());
                }
            } else {
                if (duration.equals("decreasing")) {
                    return o2.getDuration().compareTo(o1.getDuration());
                } else {
                    return o1.getDuration().compareTo(o2.getDuration());
                }
            }
        } else {
            if (duration != null) {
                if (duration.equals("decreasing")) {
                    return o2.getDuration().compareTo(o1.getDuration());
                } else {
                    return o1.getDuration().compareTo(o2.getDuration());
                }
            } else {
                if (rating.equals("decreasing")) {
                    return o2.getRating().compareTo(o1.getRating());
                } else {
                    return o1.getRating().compareTo(o2.getRating());
                }
            }
        }
    }
}
