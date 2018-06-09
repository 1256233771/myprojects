package cn.edu.swpu.data_castle_new;

import cn.edu.swpu.data_castle_new.util.thread.TimerManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataCastleNewApplication {
	public static void main(String[] args) {
		TimerManager.startManager();
		SpringApplication.run(DataCastleNewApplication.class, args);
	}
}
