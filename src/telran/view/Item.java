package telran.view;

import java.util.function.Consumer;

public interface Item {
	
	String displayName();
	void perform(InputOutput io);
	boolean isExit();
	static Item of(String name, Consumer<InputOutput> consumer, boolean exit) {
		
		return new Item() {
			
			@Override
			public void perform(InputOutput io) {
				consumer.accept(io);
			}
			
			@Override
			public boolean isExit() {
				return exit;
			}
			
			@Override
			public String displayName() {
				return name;
			}
		};
	} 
	static Item of(String name, Consumer<InputOutput> consumer) {
		
		return new Item() {
			
			@Override
			public void perform(InputOutput io) {
				consumer.accept(io);	
			}
			
			@Override
			public boolean isExit() {	
				return false;
			}
			
			@Override
			public String displayName() {
				return name;
			}
		};
	}
	
	static Item exit() {
		return of("Exit", io -> {}, true);
	}

}
