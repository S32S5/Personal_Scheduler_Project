/**
 * This source code is the source code of the personal scheduler program.
 * ※ I used a translator so the comment grammar awkward, so please understand.
 * 
 * @author S3
 * @version 0.1, first alpha version
 * @update date 2023/10/28
 **/

package scheduler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class is main class of personal scheduler source code. This class acts
 * as a GUI frame and thread.
 * 
 * @author S3
 * @version 0.1, first alpha version
 * @update date 2023/10/28
 **/
public class Main extends JFrame implements Runnable {
	private Thread dateTimeThread;
	private Thread nowScheduleThread;

	private String nowSchedule;

	private JLabel nowScheduleLabel = new JLabel("현재 스케줄이 없습니다."); // "Currently, there are no schedules."
	private JLabel todayDateLabel = new JLabel();
	private JLabel nowTime12Label = new JLabel();
	private JLabel nowTime24Label = new JLabel();

	Main() {
		// Frame Setting
		setTitle("스케줄러 프로그램"); // "Scheduler program"
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);

		Add_Schedule_Panel();
		Show_Now_Schedule_Panel();
		Show_Now_DateTime_Panel();

		pack();
	}

	/*
	 * This function displays NORTH PANEL adding a schedule.
	 */
	private void Add_Schedule_Panel() {
		add(new Add_Schedule_Controller().Add_Scheduler_Panel(), BorderLayout.NORTH);
	}

	/*
	 * This function displays CENTER PANEL showing the current schedule.
	 */
	private void Show_Now_Schedule_Panel() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);

		Font nowScheduleLabelFont = new Font("궁서체", Font.BOLD, 30);

		nowScheduleLabel.setForeground(Color.WHITE);
		nowScheduleLabel.setFont(nowScheduleLabelFont);
		nowScheduleLabel.setHorizontalAlignment(JLabel.CENTER);

		if (nowScheduleThread == null) {
			nowScheduleThread = new Thread(this);
			nowScheduleThread.start();
		}

		panel.add(nowScheduleLabel);

		add(panel, BorderLayout.CENTER);
	}

	/*
	 * This function displays SOUTH Panel showing the current date, 12-type time,
	 * 24-type time.
	 */
	private void Show_Now_DateTime_Panel() {
		JPanel panel = new JPanel();
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(3, 0));
		todayDateLabel.setHorizontalAlignment(JLabel.CENTER);
		nowTime12Label.setHorizontalAlignment(JLabel.CENTER);
		nowTime24Label.setHorizontalAlignment(JLabel.RIGHT);

		if (dateTimeThread == null) {
			dateTimeThread = new Thread(this);
			dateTimeThread.start();
		}

		labelPanel.add(todayDateLabel);
		labelPanel.add(nowTime12Label);
		labelPanel.add(nowTime24Label);
		panel.add(labelPanel);

		add(panel, BorderLayout.SOUTH);
	}

	@Override
	public void run() {
		while (true) {
			Now_DateTime_Controller ndc = new Now_DateTime_Controller();
			File_Controller fc = new File_Controller();

			// now Schedule thread part
			List<String> scheduleList = fc.readSchedules();

			try {
				if (nowSchedule == null || nowSchedule != scheduleList.get(0)) {
					nowSchedule = scheduleList.get(0);
				}

				long nowScheduleStart = Long.parseLong(nowSchedule.substring(0, 14));
				long nowScheduleEnd = Long.parseLong(nowSchedule.substring(15, 29));
				String nowScheduleText = nowSchedule.substring(30, nowSchedule.length());

				if (nowScheduleStart <= ndc.getNowDateTimeLong() && nowScheduleEnd >= ndc.getNowDateTimeLong()) {
					nowScheduleLabel.setText(nowScheduleText);
				} else {
					nowScheduleLabel.setText("현재 스케줄이 없습니다."); // "Currently, there are no schedules."
				}
			} catch (Exception e) {
			}

			// now DateTime thread part
			String todayDate = "오늘: " + ndc.getNowYearString() + "년 " + ndc.getNowMonthString() + "월 "
					+ ndc.getNowDateString() + "일" + "(" + ndc.getNowDayOfWeek() + ")";
			String nowTime12 = "현재 시간: " + "(" + ndc.getNowAmPm() + ") " + ndc.getNowHourString(12) + "시"
					+ ndc.getNowMinuteString() + "분" + ndc.getNowSecondString() + "초";
			String nowTime24 = ndc.getNowHourString(24) + "시" + ndc.getNowMinuteString() + "분"
					+ ndc.getNowSecondString() + "초";

			todayDateLabel.setText(todayDate);
			nowTime12Label.setText(nowTime12);
			nowTime24Label.setText(nowTime24);

			try {
				nowScheduleThread.sleep(500);
				dateTimeThread.sleep(500);
			} catch (Exception e) {
			}
		}
	}

	public static void main(String[] args) {
		new Main();
	}

}