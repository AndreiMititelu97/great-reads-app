package org.greatreads.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.greatreads.dto.book.BookDTO;
import org.greatreads.dto.book.BookResponseDTO;
import org.greatreads.dto.genre.GenreResponseDTO;
import org.greatreads.dto.user.UserSimpleResponseDTO;
import org.greatreads.exception.BookNotFoundException;
import org.greatreads.exception.GenreNotFoundException;
import org.greatreads.exception.UserNotFoundException;
import org.greatreads.model.Book;
import org.greatreads.model.Genre;
import org.greatreads.model.User;
import org.greatreads.model.UserToBooks;
import org.greatreads.repository.*;
import org.greatreads.service.BookService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final UserToBooksRepository userToBooksRepository;

    @Override
    public List<BookResponseDTO> getBooks() {
        List<Book> books =  bookRepository.findAll();
        List<BookResponseDTO> responseDTOList = new ArrayList<>();

        for (Book book : books) {
            responseDTOList.add(bookToDto(book));
        }

        return responseDTOList;
    }

    @Override
    public List<BookResponseDTO> getBooksByGenre(String genreName) {
        Genre genre = genreRepository.findByName(genreName).orElseThrow(() -> new GenreNotFoundException(genreName));
        List<Book> books =  bookRepository.findAllByGenre(genre);
        List<BookResponseDTO> responseDTOList = new ArrayList<>();

        for (Book book : books) {
            responseDTOList.add(bookToDto(book));
        }

        return responseDTOList;
    }

    @Override
    public BookResponseDTO getBookById(int bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        return bookToDto(book);
    }

    @Override
    @Transactional
    public BookResponseDTO addBook(BookDTO bookDTO) {
        User user = userRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(() -> new UserNotFoundException(bookDTO.getAuthorId()));
        Genre genre = genreRepository.findById(bookDTO.getGenreId())
                .orElseThrow(() -> new GenreNotFoundException(bookDTO.getGenreId()));

        Book book = new Book();
        book.setAuthor(user);
        book.setTitle(bookDTO.getTitle());
        book.setDescription(bookDTO.getDescription());
        book.setGenre(genre);
        book.setIsbn(bookDTO.getIsbn());
        book.setPublishDate(bookDTO.getPublishDate());
        book.setUrlLink(bookDTO.getUrlLink());
        book.setApproved(bookDTO.getIsApproved());
        book.setPageCover(bookDTO.getPageCover());

        bookRepository.save(book);
        return bookToDto(book);
    }

    @Override
    @Transactional
    public BookResponseDTO updateBook(int bookId, BookDTO bookDTO) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));

        if (bookDTO.getAuthorId() != null) {
            User user = userRepository.findById(bookDTO.getAuthorId())
                    .orElseThrow(() -> new UserNotFoundException(bookDTO.getAuthorId()));
            book.setAuthor(user);
        }
        if (bookDTO.getGenreId() != null) {
            Genre genre = genreRepository.findById(bookDTO.getGenreId())
                    .orElseThrow(() -> new GenreNotFoundException(bookDTO.getGenreId()));
            book.setGenre(genre);
        }

        if (bookDTO.getIsbn() != null) {
            book.setIsbn(bookDTO.getIsbn());
        }
        if (bookDTO.getTitle() != null) {
            book.setTitle(bookDTO.getTitle());
        }
        if (bookDTO.getDescription() != null) {
            book.setDescription(bookDTO.getDescription());
        }
        if (bookDTO.getPublishDate() != null) {
            book.setPublishDate(bookDTO.getPublishDate());
        }
        if (bookDTO.getUrlLink() != null) {
            book.setUrlLink(bookDTO.getUrlLink());
        }
        if (bookDTO.getIsApproved() != null) {
            book.setApproved(bookDTO.getIsApproved());
        }
        if (bookDTO.getPageCover() != null) {
            book.setPageCover(bookDTO.getPageCover());
        }

        bookRepository.save(book);
        return bookToDto(book);
    }

    @Override
    @Transactional
    public void deleteBook(int bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new BookNotFoundException(bookId);
        }
        bookRepository.deleteById(bookId);
    }

    @Override
    @Transactional
    public void uploadBook(int bookId, String url) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        book.setUrlLink(url);
        bookRepository.save(book);
    }

    @Override
    public List<BookResponseDTO> getReadBooks(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        List<UserToBooks> books =  userToBooksRepository.findByUserAndIsRead(user, true);

        List<BookResponseDTO> responseDTOList = new ArrayList<>();
        for (UserToBooks userToBooks : books) {
            responseDTOList.add(bookToDto(userToBooks.getBook()));
        }
        return responseDTOList;
    }

    @Override
    public List<BookResponseDTO> getWishlistBooks(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        List<UserToBooks> books =  userToBooksRepository.findByUserAndIsWishlist(user, true);

        List<BookResponseDTO> responseDTOList = new ArrayList<>();
        for (UserToBooks userToBooks : books) {
            responseDTOList.add(bookToDto(userToBooks.getBook()));
        }
        return responseDTOList;
    }

    private BookResponseDTO bookToDto(Book book) {
        Genre genre = book.getGenre();
        GenreResponseDTO genreDTO = new GenreResponseDTO();
        genreDTO.setId(genre.getId());
        genreDTO.setName(genre.getName());

        User user = book.getAuthor();
        UserSimpleResponseDTO userDTO = new UserSimpleResponseDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());

        BookResponseDTO bookDTO = new BookResponseDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setDescription(book.getDescription());
        bookDTO.setGenre(genreDTO);
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setAuthor(userDTO);
        bookDTO.setPublishDate(book.getPublishDate());
        bookDTO.setUrlLink(book.getUrlLink());
        bookDTO.setApproved(book.isApproved());
        bookDTO.setPageCover(book.getPageCover());
        bookDTO.setNumberOfReviews(reviewRepository.countByBook_Id(book.getId()));
        bookDTO.setRating(reviewRepository.findAverageByBook_Id(book.getId()));

        return bookDTO;
    }
}
