book_list = [
    [101, "The Da Vinci Code", "Dan Brown", 5],
    [102, "And Then There Were None", "Agatha Christie", 10],
    [103, "Harry Potter and the Philosopher's Stone", "J.K. Rowling", 5],
    [104, "The Alchemist", "Paulo Coelho", 7]
]

print(f"This is the list of {len(book_list)} books already present in the library")
print()

for book in book_list:
    print(book)
print()

print("For Manager of Library, type 'Manager' in any case.")
print("As a user of Library Books, type your Name.")


def add_books():
    print("Manager can add only one book at a time.")
    book_id = int(input("Enter Book ID: "))
    book_name = input("Enter Book name: ")
    book_writer = input("Enter name of the book writer: ")
    book_quantity = int(input("Enter the quantity of books: "))
    new_book = [book_id, book_name, book_writer, book_quantity]
    book_list.append(new_book)
    print("The set of books available in the library currently is:\n")
    print()
    for book in book_list:
        print(book)
    print()
    who_are_you()


def who_are_you():
    who_are_you_input = input("Enter Who are You? ")
    who_are_you_input = who_are_you_input.lower()
    if who_are_you_input == 'manager':
        print("You are using the library as Manager.")
        print("You can add books to the library now for the user.")
        print("Type in 'Yes' or 'No' to add Books.")
        input_by_manager = input("Do you want to add books to the library: ")
        input_by_manager = input_by_manager.lower()
        if input_by_manager == 'yes':
            add_books()
        else:
            print("Call the reader to read book.")
            who_are_you()

    else:
        global user
        who_are_you_input = who_are_you_input.capitalize()
        user = who_are_you_input
        print(f"You are using the Library as Book reader. Your name is {user} and you are here to use Library Books.\nList of books available in the library is as follows:\n")
        print("Basic Instructions Regarding Library Books:\n3 Books are allowed per user.\nBooks taken by the reader from the library need to be submitted within 14 days.\n")
        for book in book_list:
            print(book)
        print()
        book_taken_by_user()


def book_taken_by_user():
    book_taken_count = 0
    flag = 1
    number_of_books_that_user_want = int(input("Enter the number of books that you want: "))
    if 1 <= number_of_books_that_user_want <= 3:
        for _ in range(number_of_books_that_user_want):
            for i in range(len(book_list)):
                book_id_taken = int(input("Enter the book id: "))
                if book_id_taken == book_list[i][0] and book_list[i][3] > 0:
                    print("Congratulations! Your book is available and you can take it.")
                    flag = 0
                    book_taken_count += 1
                    book_list[i][3] -= 1
                if flag == 0:
                    break

                else:
                    if book_id_taken == book_list[i][0]:
                        print("Sorry, the quantity of this book ID is 0.")
                        for book in book_list:
                            print(book)
                        print()
                        book_taken_by_user()

                    else:
                        print("Sorry, No Book found! Try again.")
                        for book in book_list:
                            print(book)
                        print()
                        book_taken_by_user()
    else:
        print("Sorry, only 3 books are allowed for a user.")
        book_taken_by_user()


who_are_you()
