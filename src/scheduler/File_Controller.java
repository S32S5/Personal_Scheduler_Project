package scheduler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

/*
 * This class is responsible for managing files.
 * 
 * @author S3
 * @version 0.1, first alpha version
 * @update date 2023/10/28
 */
public class File_Controller {
	private String schedulePath = "C:\\Users\\User\\AppData\\Local\\SchedulerData\\schedule.sche"; // Default path of
																									// schedule file

	File_Controller() {
		// Check Folder exists
		File Folder = new File("C:\\Users\\User\\AppData\\Local\\SchedulerData\\");
		if (!Folder.exists())
			Folder.mkdir();

		Now_DateTime_Controller ndc = new Now_DateTime_Controller();

		try {
			long targetScheduleStart = Long.parseLong(readSchedules().get(0).substring(0, 14));
			long targetScheduleEnd = Long.parseLong(readSchedules().get(0).substring(15, 29));

			while (targetScheduleStart < ndc.getNowDateTimeLong() && targetScheduleEnd < ndc.getNowDateTimeLong()) {
				removeSchedule(0);

				targetScheduleStart = Long.parseLong(readSchedules().get(0).substring(0, 14));
				targetScheduleEnd = Long.parseLong(readSchedules().get(0).substring(15, 29));
			}
		} catch (Exception e) {
		}
	}

	/*
	 * This function read schedule file
	 * 
	 * @ return scheduleFile: type List<String>
	 */
	public List<String> readSchedules() {
		try (BufferedReader br = new BufferedReader(new FileReader(schedulePath))) {
			List<String> scheduleFile = Files.readAllLines(Paths.get(schedulePath), Charset.forName("UTF-8"));
			return scheduleFile;
		} catch (Exception ex) {
			return null;
		}
	}

	/*
	 * This function check and add new schedule.
	 * 
	 * @ param String targetSchedule: 202310281200~202310282200:Schedule
	 */
	public void addSchedule(String targetSchedule) {
		long targetScheduleStart = Long.parseLong(targetSchedule.substring(0, 14));
		long targetScheduleEnd = Long.parseLong(targetSchedule.substring(15, 29));
		String targetScheduleWork = targetSchedule.substring(30, targetSchedule.length());

		if (targetScheduleWork.equals("")) {
			JOptionPane.showMessageDialog(null, "'할 일'을 입력해 주세요.", "Error", JOptionPane.ERROR_MESSAGE); // "Please enter your 'work'."
			return;
		}

		if (targetScheduleStart >= targetScheduleEnd) {
			JOptionPane.showMessageDialog(null, "시간, 날짜를 다시 확인해 주세요.", "Error", JOptionPane.ERROR_MESSAGE); // "Please check the time and date again."
			return;
		}

		Now_DateTime_Controller ndc = new Now_DateTime_Controller();
		if (ndc.getNowDateTimeLong() >= targetScheduleEnd) {
			JOptionPane.showMessageDialog(null, "스케줄 종료 시점을 현재 시간 이후로 설정해 주세요.", "Error", JOptionPane.ERROR_MESSAGE); // "Please set the schedule end point to after the current time."
			return;
		}

		List<String> originalList = readSchedules();
		List<String> dupSchedule = new ArrayList<>();
		for (int i = 0; i < originalList.size(); i++) {
			Long checkStartDT = Long.parseLong(originalList.get(i).substring(0, 14));
			Long checkEndDT = Long.parseLong(originalList.get(i).substring(15, 29));

			if (targetScheduleStart <= checkStartDT && targetScheduleEnd >= checkStartDT
					|| targetScheduleStart <= checkEndDT && targetScheduleEnd >= checkEndDT
					|| targetScheduleStart <= checkStartDT && targetScheduleEnd >= checkEndDT
					|| targetScheduleStart >= checkStartDT && targetScheduleEnd <= checkEndDT)
				dupSchedule.add(originalList.get(i));
		}

		if (dupSchedule.size() > 0) {
			int changeSchedule = JOptionPane.showConfirmDialog(null, "시간이 겹치는 스케줄이 있습니다.\n현재 스케줄로 변경하시겠습니까?", "",
					JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE); // "There are schedules with overlapping times.\nWould you like to change to your current schedule?"

			if (changeSchedule == 0) {
				for (int i = 0; i < dupSchedule.size(); i++) {
					originalList.remove(dupSchedule.get(i));
				}
			} else {
				JOptionPane.showMessageDialog(null, "스케줄 추가를 취소하였습니다.", "", JOptionPane.PLAIN_MESSAGE); // "Addition to schedule has been cancelled."
				return;
			}
		}

		try (FileWriter fw = new FileWriter(schedulePath)) {
			originalList.add(targetSchedule);
			Collections.sort(originalList);

			String newFile = "";
			for (int i = 0; i < originalList.size(); i++) {
				newFile += originalList.get(i) + "\n";
			}

			fw.write(newFile);
			JOptionPane.showMessageDialog(null, "스케줄을 추가하였습니다.", "", JOptionPane.PLAIN_MESSAGE); // "A schedule has been added."
		} catch (Exception e) {
		}
	}

	/*
	 * This function remove a schedule at schedule file
	 * 
	 * @ param int index: 0
	 */
	public void removeSchedule(int index) {
		List<String> scheduleList = readSchedules();

		try (FileWriter fw = new FileWriter(schedulePath)) {
			scheduleList.remove(index);

			String newFile = "";
			for (int i = 0; i < scheduleList.size(); i++) {
				newFile += scheduleList.get(i) + "\n";
			}
			fw.write(newFile);
		} catch (IOException e) {
		}
	}
}