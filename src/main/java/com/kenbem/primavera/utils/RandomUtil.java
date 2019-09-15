package com.kenbem.primavera.utils;

public final class RandomUtil {


    //private static final SecureRandom RANDOM = new SecureRandom();
//    private static final char[] PASSWORD_CHARACTERS;
//    private static final boolean DEV_MODE;
//    private static final int MAX_LENGTH = 14;
//    private static final int MIN_LENGTH = 10;
//    private static final Pattern VALIDATION_PATTERN;
//
//    static {
//        List<Character> chars = new LinkedList<>();
//        IntConsumer addToList = c -> chars.add((char) c);
//        IntStream.rangeClosed('a', 'z').forEach(addToList);
//        IntStream.rangeClosed('A', 'Z').forEach(addToList);
//        IntStream.rangeClosed('0','9').forEach(addToList);
//        chars.add('#');
//        chars.add('~');
//        chars.add('!');
//        chars.add('-');
//        chars.add('_');
//        chars.add('/');
//        chars.add('^');
//        chars.add('&');
//        chars.add('+');
//        chars.add('%');
//        chars.add('(');
//        chars.add(')');
//        chars.add('=');
//
//        PASSWORD_CHARACTERS = ArrayUtils.toPrimitive(chars.toArray(new Character[0]));
//        DEV_MODE = Arrays.stream(Optional.ofNullable(System.getProperty("spring.profiles.active")).map(p -> p.split(",")).orElse(new String[0]))
//                .map(StringUtils::trim)
//                .anyMatch(Initializer.PROFILE_DEV::equals);
//        VALIDATION_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\p{Punct})(?=\\S+$).{"+MIN_LENGTH+",}$");//source: http://stackoverflow.com/a/3802238
//    }
//
//    private PasswordGenerator() {
//    }
//
//    public static String generateRandomPassword() {
//        if(DEV_MODE) {
//            return "abcd";
//        }
//        Random r = new Random();
//        int length = MIN_LENGTH + r.nextInt(MAX_LENGTH - MIN_LENGTH + 1);
//        return RandomStringUtils.random(length, 0, PASSWORD_CHARACTERS.length, false, false, PASSWORD_CHARACTERS, RANDOM);
//    }
//
//    public static boolean isValid(String password) {
//        return StringUtils.isNotBlank(password) && VALIDATION_PATTERN.matcher(password).matches();
//    }
//
//


}
