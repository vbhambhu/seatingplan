package uk.ac.ox.kir.seatingplan.utils;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;

public class SiteHelper {


    public static <T> List<T> csvToOLbjects(Class<T> type, MultipartFile multipartFile,
                                            RedirectAttributes redirectAttributes) {
        try {

            InputStream inputFS = multipartFile.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));

            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            MappingIterator<T> readValues = mapper.reader(type).with(bootstrapSchema).readValues(inputFS);
            return readValues.readAll();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
            return Collections.emptyList();
        }
    }


    public static String ucword(String input) {
        if (input == null || input.length() <= 0) {
            return input;
        }

        input = input.toLowerCase();
        char[] chars = new char[1];
        input.getChars(0, 1, chars, 0);
        if (Character.isUpperCase(chars[0])) {
            return input;
        } else {
            StringBuilder buffer = new StringBuilder(input.length());
            buffer.append(Character.toUpperCase(chars[0]));
            buffer.append(input.toCharArray(), 1, input.length()-1);
            return buffer.toString();
        }
    }


    public static String randomString(int len){

        String AB = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }


}