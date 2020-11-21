package Practice;

public class MegaBytesConverter {

    public static void main(String[] args) {

        System.out.println(getLargestPrime(45));
    }


    public static int getLargestPrime(int number) {

        if (number == 1 || number == 2)
            return -1;

        for (int i = number - 1; i > 1; i--) {
            if (number % i == 0) {
                if ((i % 2 == 0) || (i % 3 == 0) || (i % 5 == 0) || (i % 7 == 0) || (i % 11 == 0))
                    continue;
                else
                    return i;
            }
        }
        return -1;

    }

    public static boolean canPack(int bigCount, int smallCount, int goal) {

        if (bigCount < 0 || smallCount < 0 || goal < 0)
            return false;

        if ((goal < bigCount * 5 && bigCount * 5 % goal == 0) || goal == bigCount * 5)
            return true;

        goal = Math.abs(goal - bigCount * 5);


        if (goal != 0 && goal <= smallCount)
            return true;
        else
            return false;


    }

    public static void numberToWords(int number) {

        int reversedNumber = reverse(number);
        int numberOfDigits = getDigitCount(number);
        int numberOfRevDigits = getDigitCount(reversedNumber);

        if (number < 0) {
            System.out.println("Invalid Value");
        } else {


            if (reversedNumber == 0) {

                System.out.println("Zero");

            } else {

                while (reversedNumber > 0) {
                    switch (reversedNumber % 10) {

                        case 0:
                            System.out.println("Zero");
                            break;

                        case 1:
                            System.out.println("One");
                            break;

                        case 2:
                            System.out.println("Two");
                            break;

                        case 3:
                            System.out.println("Three");
                            break;

                        case 4:
                            System.out.println("Four");
                            break;

                        case 5:
                            System.out.println("Five");
                            break;

                        case 6:
                            System.out.println("Six");
                            break;

                        case 7:
                            System.out.println("Seven");
                            break;

                        case 8:
                            System.out.println("Eight");
                            break;

                        case 9:
                            System.out.println("Nine");
                            break;
                    }
                    reversedNumber /= 10;


                }
                if (numberOfDigits > numberOfRevDigits) {
                    int difference = numberOfDigits - numberOfRevDigits;
                    for (int i = 0; i < difference; i++) {
                        System.out.println("Zero");
                    }
                }
            }


        }

    }

    public static int getDigitCount(int number) {

        if (number < 0) return -1;
        if (number == 0) return 1;
        int count = 0;

        while (number > 0) {
            count++;
            number /= 10;
        }
        return count;

    }

    public static int reverse(int number) {

        int reverse = 0;
        int lastDigit;
        int n = Math.abs(number);

        while (n > 0) {
            lastDigit = n % 10;
            reverse *= 10;
            reverse += lastDigit;
            n /= 10;
        }

        return number < 0 ? -reverse : reverse;
    }


    public static void printFactors(int number) {

        if (number >= 1) {

            for (int i = 1; i <= number; i++) {

                if (number % i == 0)
                    System.out.println(i);

            }
        } else
            System.out.println("Invalid Value");


    }


    public static boolean isPerfectNumber(int number) {

        int sum = 0;

        if (number >= 1) {

            for (int i = 1; i < number; i++) {

                if (number % i == 0)
                    sum += i;
            }

            return number == sum;
        } else
            return false;


    }

    public static int getGreatestCommonDivisor(int first, int second) {

        if (first < 10 || second < 10)
            return -1;

        for (int i = first < second ? first : second; i >= 0; i--) {

            if (first % i == 0 && second % i == 0)
                return i;

        }
        return -1;

    }

    public static boolean hasSharedDigit(int a, int b) {


        if (!(a >= 10 && a <= 99) || !(b >= 10 && b <= 99)) {
            return false;
        }

        int reset = b;

        while (a > 0) {
            while (b > 0) {
                if (a % 10 == b % 10) {
                    return true;
                }

                b /= 10;

            }
            b = reset;
            a /= 10;

        }
        return false;
    }


    public static boolean hasSameLastDigit(int a, int b, int c) {

        if (!isValid(a) || !isValid(b) || !isValid(c))
            return false;
        return (a % 10 == b % 10) || (a % 10 == c % 10) || (b % 10 == c % 10);
    }

    public static boolean isValid(int a) {
        return ((a >= 10 && a <= 1000));

    }

    public static int getEvenDigitSum(int number) {

        if (number < 0) {
            return -1;
        }

        int sum = 0;
        int currentDigit;

        while (number > 0) {
            currentDigit = number % 10;
            if (currentDigit % 2 == 0) {
                sum += currentDigit;
            }
            number /= 10;
        }

        return sum;
    }


    public static boolean isPalindrome(int number) {

        int reverse = 0;
        int lastDigit;
        int n = Math.abs(number);

        while (n > 0) {
            lastDigit = n % 10;
            reverse *= 10;
            reverse += lastDigit;
            n /= 10;
        }

        return reverse == Math.abs(number);
    }

    public static int sumFirstAndLastDigit(int number) {

        if (number < 0) {
            return -1;
        }

        int firstNumber = 0;
        int secondNumber = 0;
        int currentDigit;
        int count = 0;


        while (number > 0) {
            currentDigit = number % 10;
            if (count == 0) {
                secondNumber = currentDigit;
            }
            firstNumber = currentDigit;

            number /= 10;
            count++;

        }

        return secondNumber + firstNumber;
    }


    public static void sumDigits(int d) {

        System.out.println(123 / 10);
    }


    public static void printMegaBytesAndKiloBytes(int kiloBytes) {

        if (kiloBytes < 0) {
            System.out.println("Invalid Value");
        }

        System.out.println(kiloBytes + " KB = " + kiloBytes / 1024 + " MB" + kiloBytes % 1024 + " KB");
    }

    public static double area(double radius) {
        if (radius < 0)
            return -1.0;
        else
            return radius * radius * Math.PI;
    }

    public static double area(double x, double y) {
        if (x < 0 || y < 0)
            return -1.0;
        else
            return x * y;
    }

    public static void printYearsAndDays(long minutes) {

        if (minutes < 0)
            System.out.println("Invalid Value");
        else
            System.out.println(minutes + " min = " + minutes / (60 * 24 * 365) + " y and " + minutes % (60 * 24 * 365) / (60 * 24) + " d");

    }

    public static void printEqual(int a, int b, int c) {

        if (a < 0 || b < 0 || c < 0)
            System.out.println("Invalid Value");
        else if (a == b && b == c)
            System.out.println("All numbers are equal");
        else if ((a == b && b != c) || (a == c && a != b) || (b == c && a != c))
            System.out.println("Neither all are equal or different");
        else
            System.out.println("All numbers are different");

    }

    public static boolean isCatPlaying(boolean summer, int temperature) {
        return (!summer && (temperature >= 25 && temperature <= 35)) || (summer && (temperature >= 25 && temperature <= 45));

    }

    public static void switchTest() {
        char switchValue = 'a';

        switch (switchValue) {

            case 'A':
                System.out.println("got A");
                break;

        }
    }

    public static void printDayOfTheWeek(int day) {

        switch (day) {
            case 0:
                System.out.println("Sunday");
                break;
            case 1:
                System.out.println("Monday");
                break;
            case 2:
                System.out.println("Tuesday");
                break;
            case 3:
                System.out.println("Wednesday");
                break;
            case 4:
                System.out.println("Thursday");
                break;
            case 5:
                System.out.println("Friday");
                break;
            case 6:
                System.out.println("Saturday");
                break;
            default:
                System.out.println("Invalid day");
                break;

        }
    }


    public static String printNumberInWord(int number) {

        switch (number) {
            case 0:
                return "ZERO";

            case 1:
                return "ONE";

            case 2:
                return "TWO";

            case 3:
                return "THREE";
            case 4:
                return "FOUR";

            case 5:
                return "FIVE";

            case 6:
                return "SIX";

            case 7:
                return "SEVEN";

            case 8:
                return "EIGHT";

            case 9:
                return "NINE";

            default:
                return "OTHER";


        }
    }

    public static boolean isLeapYear(int year) {
        if (year >= 1 && year <= 9999) {
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                return true;
            else
                return false;
        } else
            return false;
    }

    public static int getDaysInMonth(int month, int year) {

        if (year < 1 || year > 9999)
            return -1;

        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (isLeapYear(year)) return 29;
                else return 28;
            default:
                return -1;
        }
    }

    public static void divisibleByThreeAndFive() {
        int sum = 0;
        int count = 0;
        for (int i = 1; i <= 1000; i++) {

            if (i % 3 == 0 && i % 5 == 0) {
                count++;
                sum = sum + i;
            }

            if (count == 5) {
                break;
            }

        }

        System.out.println(sum);

    }

    public static boolean isOdd(int i) {
        return (!(i % 2 == 0) && i > 0);
    }

    public static int sumOdd(int a, int b) {

        int sum = 0;

        if (a <= b && a >= 0 && b >= 0) {
            for (int i = a; i <= b; i++) {
                if (isOdd(i))
                    sum += i;
            }
        } else
            return -1;

        return sum;
    }


}
