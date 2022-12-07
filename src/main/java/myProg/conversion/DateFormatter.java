package myProg.conversion;

import org.springframework.context.MessageSource;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter implements Formatter<Date> {
    private final MessageSource messageSource;

    public DateFormatter(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        return createDateFormat(locale).parse(text);
    }

    @Override
    public String print(Date object, Locale locale) {
        return createDateFormat(locale).format(object);
    }

    private SimpleDateFormat createDateFormat(Locale locale) {
        final String format = messageSource.getMessage("date.format", null, locale);
        final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        return dateFormat;
    }
}