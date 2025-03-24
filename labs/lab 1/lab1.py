"""lab1.py
A simple program demonstrating basic language features.
"""

# Function Definition
def contains(items: list[str], desired: str) -> bool:
    """Return true if the list contains the desired item."""

    # Iteration on elements
    for item in items:

        # Conditional
        if item == desired:
            return True

    return False


if __name__ == '__main__':
    # Variables
    x = 3
    y = 4.0
    z = int((x ** 2 + y ** 2) ** (1 / 2))

    # String Concatenation
    print('x: ' + str(x))

    # String Formatting
    formatted = f'y: {y}'
    print(formatted)

    # Printing on Same Line
    print('z: ', end='')
    print(z)

    # Strings and Characters
    a = 'hello'
    b = 'j'
    c = a.replace(a[0], b)

    # Iteration in Indices
    for i in range(len(c)):
        print(c[i], end='')  # Print on same line
    print()  # Print the line's end

    # List Usage
    cats = ['Mochi', 'harvest']
    cats.pop(0)
    cats.append('Pearl')

    # Function Call
    has_mochi = contains(cats, 'Mochi')
    if not has_mochi:
        print('Bye bye, Mochi! Farewell!')
