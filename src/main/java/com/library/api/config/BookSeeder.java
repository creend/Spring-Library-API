package com.library.api.config;

import com.library.api.entity.BookEntity;
import com.library.api.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class BookSeeder {

    @Bean
    CommandLineRunner commandLineRunner(BookRepository bookRepository){
        return args -> {
            BookEntity[] books = new BookEntity[20];

            books[0] = new BookEntity(
                    "To Kill a Mockingbird",
                    "Harper Lee",
                    "Fiction",
                    "HarperCollins",
                    LocalDate.of(1960, Month.JULY, 11),
                    10,
                    25.99
            );

            books[1] = new BookEntity(
                    "1984",
                    "George Orwell",
                    "Dystopian",
                    "Penguin Books",
                    LocalDate.of(1949, Month.JUNE, 8),
                    10,
                    19.99
            );

            books[2] = new BookEntity(
                    "The Great Gatsby",
                    "F. Scott Fitzgerald",
                    "Fiction",
                    "Scribner",
                    LocalDate.of(1925, Month.APRIL, 10),
                    10,
                    14.99
            );

            books[3] = new BookEntity(
                    "Pride and Prejudice",
                    "Jane Austen",
                    "Romance",
                    "Penguin Classics",
                    LocalDate.of(1813, Month.JANUARY, 28),
                    15,
                    12.49
            );

            books[4] = new BookEntity(
                    "The Catcher in the Rye",
                    "J.D. Salinger",
                    "Fiction",
                    "Little, Brown and Company",
                    LocalDate.of(1951, Month.JULY, 16),
                    20,
                    21.99
            );

            books[5] = new BookEntity(
                    "Brave New World",
                    "Aldous Huxley",
                    "Dystopian",
                    "Chatto & Windus",
                    LocalDate.of(1932, Month.JUNE, 17),
                    25,
                    18.75
            );

            books[6] = new BookEntity(
                    "The Hobbit",
                    "J.R.R. Tolkien",
                    "Fantasy",
                    "Allen & Unwin",
                    LocalDate.of(1937, Month.SEPTEMBER, 21),
                    10,
                    23.50
            );

            books[7] = new BookEntity(
                    "Harry Potter and the Sorcerer's Stone",
                    "J.K. Rowling",
                    "Fantasy",
                    "Scholastic",
                    LocalDate.of(1997, Month.JUNE, 26),
                    10,
                    29.99
            );

            books[8] = new BookEntity(
                    "Fahrenheit 451",
                    "Ray Bradbury",
                    "Dystopian",
                    "Ballantine Books",
                    LocalDate.of(1953, Month.OCTOBER, 19),
                    10,
                    17.25
            );

            books[9] = new BookEntity(
                    "Lord of the Flies",
                    "William Golding",
                    "Fiction",
                    "Faber and Faber",
                    LocalDate.of(1954, Month.SEPTEMBER, 17),
                    10,
                    16.80
            );

            books[10] = new BookEntity(
                    "The Da Vinci Code",
                    "Dan Brown",
                    "Mystery",
                    "Doubleday",
                    LocalDate.of(2003, Month.MARCH, 18),
                    10,
                    14.95
            );

            books[11] = new BookEntity(
                    "The Alchemist",
                    "Paulo Coelho",
                    "Fantasy",
                    "HarperOne",
                    LocalDate.of(1988, Month.JUNE, 17),
                    10,
                    12.00
            );

            books[12] = new BookEntity(
                    "The Lord of the Rings: The Fellowship of the Ring",
                    "J.R.R. Tolkien",
                    "Fantasy",
                    "Allen & Unwin",
                    LocalDate.of(1954, Month.JULY, 29),
                    10,
                    27.50
            );

            books[13] = new BookEntity(
                    "The Chronicles of Narnia: The Lion, the Witch and the Wardrobe",
                    "C.S. Lewis",
                    "Fantasy",
                    "Geoffrey Bles",
                    LocalDate.of(1950, Month.OCTOBER, 16),
                    10,
                    19.95
            );

            books[14] = new BookEntity(
                    "Gone with the Wind",
                    "Margaret Mitchell",
                    "Historical Fiction",
                    "Macmillan Publishers",
                    LocalDate.of(1936, Month.JUNE, 30),
                    10,
                    22.75
            );

            books[15] = new BookEntity(
                    "Moby-Dick",
                    "Herman Melville",
                    "Adventure",
                    "Richard Bentley",
                    LocalDate.of(1851, Month.OCTOBER, 18),
                    10,
                    18.50
            );

            books[16] = new BookEntity(
                    "The Hunger Games",
                    "Suzanne Collins",
                    "Dystopian",
                    "Scholastic",
                    LocalDate.of(2008, Month.SEPTEMBER, 14),
                    10,
                    16.99
            );

            books[17] = new BookEntity(
                    "The Shining",
                    "Stephen King",
                    "Horror",
                    "Doubleday",
                    LocalDate.of(1977, Month.JANUARY, 28),
                    10,
                    20.25
            );

            books[18] = new BookEntity(
                    "The Road",
                    "Cormac McCarthy",
                    "Dystopian",
                    "Alfred A. Knopf",
                    LocalDate.of(2006, Month.SEPTEMBER, 26),
                    10,
                    15.80
            );

            books[19] = new BookEntity(
                    "War and Peace",
                    "Leo Tolstoy",
                    "Historical Fiction",
                    "The Russian Messenger",
                    LocalDate.of(1869, Month.JANUARY, 25),
                    10,
                    28.00
            );

            bookRepository.saveAll(List.of(books));
        };
    }
}
