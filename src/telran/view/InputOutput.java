package telran.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.swing.text.DateFormatter;

public interface InputOutput {
	
	String readString(String promt);
	void writeString(Object object);
	
	default void writeLine(Object object) {
		writeString(object.toString() + "\n");
	}
	
	default <R> R readObject(String prompt, String errorPrompt, Function<String, R> mapper) {
		boolean running = true;
		R result = null;
		while (running) {
			try {
				String str = readString(prompt);
				result = mapper.apply(str);
				running = false;
			} catch (Exception e) {
				writeLine(errorPrompt + " - " + e.getMessage());
			}
		}
		return result;
		
	}
	
	default String readStringPredicate(String prompt, String errorPrompt, Predicate<String> predicat) throws Exception {
		
	return readObject(prompt, errorPrompt, s -> {
		if (!predicat.test(s)) {
			throw new RuntimeException("Wrong string format");
		}
		return s;
	});
	}
	
	default String readStringOptions(String prompt, String errorPrompt, Set<String> options) {
		
		return readObject(prompt, errorPrompt, s -> {
			if (!options.contains(s)) {
				throw new RuntimeException();
			}
			return s;
		});
		
	}
	
	default int readInt(String prompt, String errorPrompt) {
		
		return readInt(prompt, errorPrompt, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	default int readInt(String prompt, String errorPrompt, int min, int max) {
		
		return readObject(prompt, errorPrompt, (s) -> {
			try {
				int res = Integer.parseInt(s);
				checkRange(min, max, res);
				return res;
			} catch (NumberFormatException e) {
				throw new RuntimeException("Not number");
			}
		});
	}
	default void checkRange(double min, double max, double res) {
		if (res < min || res > max) {
			 throw new RuntimeException("Wrong range");
		}
	}
	
	default long readLong(String prompt, String errorPrompt, long min, long max) {
	
		return readObject(prompt, errorPrompt, (s) -> {
			try {
				long res = Long.parseLong(s);
				checkRange(min, max, res);
				return res;
			} catch (NumberFormatException e) {
				throw new RuntimeException("Not number");
			}
		});
	}
	
	default LocalDate readDateISO(String prompt, String errorPrompt) {
		
		return readDate(prompt, errorPrompt, "yyyy-MM-dd", LocalDate.MIN, LocalDate.MAX);
	}
	
	default LocalDate readDate(String prompt, String errorPrompt, String format,
			LocalDate min, LocalDate max) {
		
		return readObject(prompt, errorPrompt, (s) -> {
			DateTimeFormatter dtf = null;
			try {
				dtf = DateTimeFormatter.ofPattern(format);
			} catch (Exception e) {
				throw new RuntimeException("Wrong date format " + format);
			}
			LocalDate res = LocalDate.parse(s, dtf);
			if (res.isBefore(min)) {
				throw new RuntimeException("must not be before " + min.format(dtf));
			}
			if (res.isAfter(max)) {
				throw new RuntimeException("must not be after " + max.format(dtf));
			}
			return res;
		});
	}
	
	default double readNumber (String prompt, String errorPrompt, double min, double max) {
		
		return readObject(prompt, errorPrompt, (s) -> {
			try {
				double res = Double.parseDouble(s);
				checkRange(min, max, res);
				return res;
			} catch (NumberFormatException e) {
				throw new RuntimeException("Not number");
			}
		});
		
	}

}
