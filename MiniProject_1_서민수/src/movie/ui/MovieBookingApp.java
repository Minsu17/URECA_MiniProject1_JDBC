package movie.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import movie.dao.BookingDAO;
import movie.dao.MovieDAO;
import movie.dao.ReviewDAO;
import movie.dto.Booking;
import movie.dto.Movie;
import movie.dto.Review;

public class MovieBookingApp extends JFrame {
    private String username;
    private List<Movie> movies;
    private JList<Movie> movieJList;
    private DefaultListModel<Movie> movieListModel;
    private MovieDAO movieDAO;
    private BookingDAO bookingDAO;
    private ReviewDAO reviewDAO;

    public MovieBookingApp(String username) {
        this.username = username;
        movieDAO = new MovieDAO();
        bookingDAO = new BookingDAO();
        reviewDAO = new ReviewDAO();

        setTitle("영화 예매 프로그램");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        loadMovies();

        movieListModel = new DefaultListModel<>();
        for (Movie movie : movies) {
            movieListModel.addElement(movie);
        }

        JLabel titleLabel = new JLabel("영화 목록", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        movieJList = new JList<>(movieListModel);
        movieJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane movieScrollPane = new JScrollPane(movieJList);

        JButton bookButton = new JButton("영화 예매");
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookSelectedMovie();
            }
        });

        JButton viewBookingsButton = new JButton("예매 목록");
        viewBookingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewBookings();
            }
        });

        JButton cancelBookingButton = new JButton("예매 취소");
        cancelBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelBooking();
            }
        });

        JButton reviewButton = new JButton("리뷰 관리");
        reviewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manageReviews();
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        buttonPanel.add(bookButton);
        buttonPanel.add(viewBookingsButton);
        buttonPanel.add(cancelBookingButton);
        buttonPanel.add(reviewButton);

        add(movieScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadMovies() {
        movies = movieDAO.getAllMovies();
    }

    private void bookSelectedMovie() {
        Movie selectedMovie = movieJList.getSelectedValue();
        if (selectedMovie != null) {
            boolean isBooked = bookingDAO.bookMovie(username, selectedMovie.getId());
            if (isBooked) {
                JOptionPane.showMessageDialog(this, "영화 예매 성공");
            } else {
                JOptionPane.showMessageDialog(this, "영화 예매 실패");
            }
        } else {
            JOptionPane.showMessageDialog(this, "예매할 영화를 선택해주세요");
        }
    }

    private void viewBookings() {
        List<Booking> bookings = bookingDAO.getUserBookings(username);
        if (bookings.isEmpty()) {
            JOptionPane.showMessageDialog(this, "예매한 영화가 없습니다");
            return;
        }

        String[] columnNames = {"ID", "영화 제목", "예매 날짜"};
        Object[][] data = new Object[bookings.size()][3];
        for (int i = 0; i < bookings.size(); i++) {
            Booking booking = bookings.get(i);
            data[i][0] = booking.getId();
            data[i][1] = booking.getMovieTitle();
            data[i][2] = booking.getBookingDate();
        }

        JTable table = new JTable(new DefaultTableModel(data, columnNames));
        JOptionPane.showMessageDialog(this, new JScrollPane(table), "예매 목록", JOptionPane.PLAIN_MESSAGE);
    }

    private void cancelBooking() {
        String input = JOptionPane.showInputDialog(this, "취소할 예매 ID:");
        if (input != null) {
            try {
                int bookingId = Integer.parseInt(input);
                boolean isCancelled = bookingDAO.cancelBooking(bookingId);
                if (isCancelled) {
                    JOptionPane.showMessageDialog(this, "예매 취소 완료");
                } else {
                    JOptionPane.showMessageDialog(this, "예매 취소 실패");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "유효하지 않은 예매 ID입니다.");
            }
        }
    }

    private void manageReviews() {
        Movie selectedMovie = movieJList.getSelectedValue();
        if (selectedMovie != null) {
            new ReviewManagementFrame(selectedMovie).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "리뷰를 관리할 영화를 선택해주세요");
        }
    }

    public static void main(String[] args) {
        new MovieBookingApp("john").setVisible(true);
    }

    private class ReviewManagementFrame extends JFrame {
        private Movie movie;
        private DefaultListModel<Review> reviewListModel;
        private JList<Review> reviewJList;

        public ReviewManagementFrame(Movie movie) {
            this.movie = movie;
            setTitle("리뷰 관리 - " + movie.getTitle());
            setSize(600, 400);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            reviewListModel = new DefaultListModel<>();
            loadReviews();

            reviewJList = new JList<>(reviewListModel);
            reviewJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane reviewScrollPane = new JScrollPane(reviewJList);

            JButton addReviewButton = new JButton("리뷰 작성");
            addReviewButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addReview();
                }
            });

            JButton updateReviewButton = new JButton("리뷰 수정");
            updateReviewButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateReview();
                }
            });

            JButton deleteReviewButton = new JButton("리뷰 삭제");
            deleteReviewButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    deleteReview();
                }
            });

            JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
            buttonPanel.add(addReviewButton);
            buttonPanel.add(updateReviewButton);
            buttonPanel.add(deleteReviewButton);

            add(reviewScrollPane, BorderLayout.CENTER);
            add(buttonPanel, BorderLayout.SOUTH);
        }

        private void loadReviews() {
            List<Review> reviews = reviewDAO.getReviews(movie.getId());
            reviewListModel.clear();
            for (Review review : reviews) {
                reviewListModel.addElement(review);
            }
        }

        private void addReview() {
            JTextField ratingField = new JTextField();
            JTextArea commentArea = new JTextArea(5, 20);
            JPanel panel = new JPanel(new GridLayout(2, 2));
            panel.add(new JLabel("평점:"));
            panel.add(ratingField);
            panel.add(new JLabel("댓글:"));
            panel.add(new JScrollPane(commentArea));

            int result = JOptionPane.showConfirmDialog(this, panel, "리뷰 작성", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    int rating = Integer.parseInt(ratingField.getText());
                    String comment = commentArea.getText();
                    boolean isAdded = reviewDAO.addReview(movie.getId(), username, rating, comment);
                    if (isAdded) {
                        JOptionPane.showMessageDialog(this, "리뷰 작성 성공");
                        loadReviews();
                    } else {
                        JOptionPane.showMessageDialog(this, "리뷰 작성 실패");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "유효하지 않은 평점입니다.");
                }
            }
        }

        private void updateReview() {
            Review selectedReview = reviewJList.getSelectedValue();
            if (selectedReview != null) {
                JTextField ratingField = new JTextField(String.valueOf(selectedReview.getRating()));
                JTextArea commentArea = new JTextArea(selectedReview.getComment(), 5, 20);
                JPanel panel = new JPanel(new GridLayout(2, 2));
                panel.add(new JLabel("평점:"));
                panel.add(ratingField);
                panel.add(new JLabel("댓글:"));
                panel.add(new JScrollPane(commentArea));

                int result = JOptionPane.showConfirmDialog(this, panel, "리뷰 수정", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        int rating = Integer.parseInt(ratingField.getText());
                        String comment = commentArea.getText();
                        boolean isUpdated = reviewDAO.updateReview(selectedReview.getId(), rating, comment);
                        if (isUpdated) {
                            JOptionPane.showMessageDialog(this, "리뷰 수정 성공");
                            loadReviews();
                        } else {
                            JOptionPane.showMessageDialog(this, "리뷰 수정 실패");
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "유효하지 않은 평점입니다.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "수정할 리뷰를 선택해주세요");
            }
        }

        private void deleteReview() {
            Review selectedReview = reviewJList.getSelectedValue();
            if (selectedReview != null) {
                int result = JOptionPane.showConfirmDialog(this, "리뷰를 삭제하시겠습니까?", "리뷰 삭제", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    boolean isDeleted = reviewDAO.deleteReview(selectedReview.getId());
                    if (isDeleted) {
                        JOptionPane.showMessageDialog(this, "리뷰 삭제 성공");
                        loadReviews();
                    } else {
                        JOptionPane.showMessageDialog(this, "리뷰 삭제 실패");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "삭제할 리뷰를 선택해주세요");
            }
        }
    }
}
