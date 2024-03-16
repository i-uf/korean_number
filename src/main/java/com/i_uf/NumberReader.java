package com.i_uf;

import java.util.Scanner;
import java.util.function.BiPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberReader {
    private static final Pattern pattern = Pattern.compile("-?[0-9]*(,[0-9]+)*[0-9]");
    private static final String num = "영일이삼사오육칠팔구";
    private static final String[] ten_kilo = new String[] {
            "", "만 ", "억 ", "조 ", "경 ",
            "해 ", "자 ", "양 ", "구 ",
            "간 ", "정 ", "재 ", "극 ",
            "항하사 ", "아승기 ", "나유타 ",
            "불가사의 ", "무량대수 "
    };
    private static final BiPredicate<String, Integer> checkIndex = (target, index) ->
            target.length() > index && index >= 0 && target.charAt(index) != '0';
    private static final BiPredicate<String, Integer> hyperCheck = (target, index) ->
            checkIndex.test(target, index) || checkIndex.test(target, index + 1) ||
                checkIndex.test(target, index + 2) || checkIndex.test(target, index + 3);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("숫자를 입력하시오. exit를 입력하면 종료됩니다.");
        while (true) {
            String s = scanner.nextLine();
            if(s.equals("exit")) break;
            Matcher match = pattern.matcher(s);
            if(match.matches()) {
                String number = s.replaceAll("[,-]", "");
                System.out.println(number);
                while(number.startsWith("0")) number = number.substring(1);
                if(number.isEmpty()) System.out.println("영");
                boolean negative = s.startsWith("-");
                if(number.length() <= 72) {
                    StringBuilder result = new StringBuilder();
                    int a = number.length();
                    for(int i = (number.length()-1) / 4 + 1; i > 0; i--) {
                        if(checkIndex.test(number, a - i * 4))
                            result.append(num.charAt(number.charAt(a - i * 4) - '0')).append("천");
                        if(checkIndex.test(number, a - i * 4 + 1))
                            result.append(num.charAt(number.charAt(a - i * 4 + 1)  - '0')).append("백");
                        if(checkIndex.test(number, a - i * 4 + 2))
                            result.append(num.charAt(number.charAt(a - i * 4 + 2)  - '0')).append("십");
                        if(checkIndex.test(number, a - i * 4 + 3))
                            result.append(num.charAt(number.charAt(a - i * 4 + 3) - '0'));
                        if(i > 1 && hyperCheck.test(number, a - i * 4)) result.append(ten_kilo[i-1]);
                    }
                    System.out.println((negative?"마이너스 ":"") + result);
                }
                else {
                    System.out.printf("범위 초과! %s > 72\n", number.length());
                }
            }
            else {
                System.out.println("숫자, 하이픈,, 만 사용하여");
            }
        }
    }
}
