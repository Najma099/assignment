def validate_password(password, username, last_three_passwords):
    # Check minimum length and character variety
    if len(password) < 10 or not (
        any(c.isupper() for c in password) and
        any(c.islower() for c in password) and
        any(c.isdigit() for c in password) and
        any(c in "!@#$%^&*" for c in password)
    ):
        return False, "Password must be at least 10 characters long and contain two uppercase letters, two lowercase letters, two digits, and two special characters."
    
    # Check sequence and repetition restrictions
    if any(password[i] == password[i+1] == password[i+2] for i in range(len(password) - 2)):
        return False, "Password cannot contain sequences of three or more repeating characters."

    # Check for username sequence
    if any(username[i:i+3] in password for i in range(len(username) - 2)):
        return False, "Password cannot contain sequences of three or more consecutive characters from the username."

    # Check historical password
    if password in last_three_passwords:
        return False, "Password cannot be the same as any of the last three passwords used."
    
    return True, "Password is valid."


def main():
    username = input("Enter your username: ")
    last_three_passwords = []  
    while True:
        password = input("Enter your new password: ")
        is_valid, message = validate_password(password, username, last_three_passwords)
        if is_valid:
            last_three_passwords.append(password) 
            if len(last_three_passwords) > 3:
                last_three_passwords.pop(0) 
            print("Password is valid!")
            break
        else:
            print("Invalid Password:", message)

if __name__ == "__main__":
    main()
