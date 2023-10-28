package scheduler;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * This class is main class control add schedule panel
 * 
 * @author S3
 * @version 0.1, first alpha version
 * @update date 2023/10/28
 **/
public class Add_Schedule_Controller {
	private String[] yearList, monthList, hourListAmPm, hourList24, dateList28, dateList29, dateList30, dateList31,
			timeList60;
	private JRadioButton type12RB, sAmRB = new JRadioButton("오전"), sPmRB = new JRadioButton("오후"),
			eAmRB = new JRadioButton("오전"), ePmRB = new JRadioButton("오후"), type24RB;
	private JComboBox<String> sYCB12, sMCB12, sDCB12, shCB12, smCB12, ssCB12, eYCB12, eMCB12, eDCB12, ehCB12, emCB12,
			esCB12, sYCB24, sMCB24, sDCB24, shCB24, smCB24, ssCB24, eYCB24, eMCB24, eDCB24, ehCB24, emCB24, esCB24;
	private JTextField workTF12, workTF24;
	private JButton addButton, cancelButton;

	File_Controller fc = new File_Controller();

	Add_Schedule_Controller() {
		Now_DateTime_Controller dc = new Now_DateTime_Controller();

		yearList = new String[100];
		for (int i = dc.getNowYearInt(); i < (dc.getNowYearInt() + 100); i++) {
			yearList[i - dc.getNowYearInt()] = Integer.toString(i);
		}

		timeList60 = new String[60];
		for (int i = 0; i < 60; i++) {
			timeList60[i] = String.format("%02d", (i));
		}

		hourList24 = Arrays.copyOf(timeList60, 24);

		hourListAmPm = Arrays.copyOf(timeList60, 12);
		hourListAmPm[0] = "12";

		// dateTimeList31
		dateList31 = new String[31];
		for (int i = 0; i < 31; i++) {
			dateList31[i] = String.format("%02d", (i + 1));
		}

		dateList30 = Arrays.copyOf(dateList31, 30);

		dateList29 = Arrays.copyOf(dateList31, 29);

		dateList28 = Arrays.copyOf(dateList31, 28);

		monthList = Arrays.copyOf(dateList31, 12);
	}

	/*
	 * This function add scheduler panel
	 */
	public JPanel Add_Scheduler_Panel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// addScheduleLabel
		JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel addScheduleLabel = new JLabel("스케줄 추가"); // "Add schedule"
		addScheduleLabel.setFont(new Font("Serif", Font.BOLD, 20));
		addScheduleLabel.setForeground(Color.PINK);

		labelPanel.add(addScheduleLabel);
		panel.add(labelPanel);

		// addSchedulePanelType12
		panel.add(addSchedulePanelType12());

		// addSchedulePanelType24
		panel.add(addSchedulePanelType24());

		ButtonGroup bg = new ButtonGroup();
		bg.add(type12RB);
		bg.add(type24RB);

		// addScheduleCancelSchedule
		panel.add(addScheduleCancelSchedule());

		init();

		return panel;
	}

	/*
	 * This function add scheduler panel type 12
	 */
	private JPanel addSchedulePanelType12() {
		JPanel panel = new JPanel();
		type12RB = new JRadioButton("(12)");
		type12RB.setVerticalTextPosition(JRadioButton.BOTTOM);
		type12RB.setHorizontalTextPosition(JRadioButton.LEFT);
		type12RB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				type12Enabled();
			}
		});
		panel.add(type12RB);

		// Start Year
		sYCB12 = new JComboBox<String>(yearList);
		sYCB12.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				dateChanger("s12");
			}
		});
		panel.add(sYCB12);
		panel.add(new JLabel("년")); // "Year"

		// Start Month
		sMCB12 = new JComboBox<String>(monthList);
		sMCB12.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				dateChanger("s12");
			}
		});
		panel.add(sMCB12);
		panel.add(new JLabel("월")); // "Month"

		// Start Date
		sDCB12 = new JComboBox<String>();
		panel.add(sDCB12);
		panel.add(new JLabel("일")); // "Date"

		// Start AmPm
		JPanel sAmPmP = new JPanel();
		sAmPmP.setLayout(new BoxLayout(sAmPmP, BoxLayout.Y_AXIS));
		sAmRB.setVerticalTextPosition(JRadioButton.BOTTOM);
		sPmRB.setVerticalTextPosition(JRadioButton.BOTTOM);

		ButtonGroup sBg = new ButtonGroup();
		sBg.add(sAmRB);
		sBg.add(sPmRB);

		sAmPmP.add(sAmRB);
		sAmPmP.add(sPmRB);
		panel.add(sAmPmP);

		// Start Hour
		shCB12 = new JComboBox<String>(hourListAmPm);
		panel.add(shCB12);
		panel.add(new JLabel("시")); // "Hour"

		// Start Minute
		smCB12 = new JComboBox<String>(timeList60);
		panel.add(smCB12);
		panel.add(new JLabel("분")); // "Minute"

		// Start Second
		ssCB12 = new JComboBox<String>(timeList60);
		panel.add(ssCB12);
		panel.add(new JLabel("초  ~ ")); // "Second ~ "

		// End Year
		eYCB12 = new JComboBox<String>(yearList);
		eYCB12.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				dateChanger("e12");
			}
		});
		panel.add(eYCB12);
		panel.add(new JLabel("년")); // "Year"

		// End Month
		eMCB12 = new JComboBox<String>(monthList);
		eMCB12.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				dateChanger("e12");
			}
		});
		panel.add(eMCB12);
		panel.add(new JLabel("월")); // "Month"

		// End Date
		eDCB12 = new JComboBox<String>();
		panel.add(eDCB12);
		panel.add(new JLabel("일")); // "Date"

		// Start AmPm
		JPanel eAmPmP = new JPanel();
		eAmPmP.setLayout(new BoxLayout(eAmPmP, BoxLayout.Y_AXIS));
		eAmRB.setVerticalTextPosition(JRadioButton.BOTTOM);
		ePmRB.setVerticalTextPosition(JRadioButton.BOTTOM);

		ButtonGroup eBg = new ButtonGroup();
		eBg.add(eAmRB);
		eBg.add(ePmRB);

		eAmPmP.add(eAmRB);
		eAmPmP.add(ePmRB);
		panel.add(eAmPmP);

		// End Hour
		ehCB12 = new JComboBox<String>(hourListAmPm);
		panel.add(ehCB12);
		panel.add(new JLabel("시")); // "Hour"

		// End Minute
		emCB12 = new JComboBox<String>(timeList60);
		panel.add(emCB12);
		panel.add(new JLabel("분")); // "Minute"

		// End Second
		esCB12 = new JComboBox<String>(timeList60);
		panel.add(esCB12);
		panel.add(new JLabel("초  ")); // "Second "

		// Work
		panel.add(new JLabel("할 일")); // "Work"
		workTF12 = new JTextField(10);
		panel.add(workTF12);

		return panel;
	}

	/*
	 * This function add scheduler panel type 24
	 */
	private JPanel addSchedulePanelType24() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		type24RB = new JRadioButton("(24)");
		type24RB.setVerticalTextPosition(JRadioButton.BOTTOM);
		type24RB.setHorizontalTextPosition(JRadioButton.LEFT);
		type24RB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				type24Enabled();
			}
		});
		panel.add(type24RB);

		// Start Year
		sYCB24 = new JComboBox<String>(yearList);
		sYCB24.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				dateChanger("s24");
			}
		});
		panel.add(sYCB24);
		panel.add(new JLabel("년")); // "Year"

		// Start Month
		sMCB24 = new JComboBox<String>(monthList);
		sMCB24.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				dateChanger("s24");
			}
		});
		panel.add(sMCB24);
		panel.add(new JLabel("월")); // "Month"

		// Start Date
		sDCB24 = new JComboBox<String>();
		panel.add(sDCB24);
		panel.add(new JLabel("일")); // "Date"

		// Start Hour
		shCB24 = new JComboBox<String>(hourList24);
		panel.add(shCB24);
		panel.add(new JLabel("시")); // "Hour"

		// Start Minute
		smCB24 = new JComboBox<String>(timeList60);
		panel.add(smCB24);
		panel.add(new JLabel("분")); // "Minute"

		// Start Second
		ssCB24 = new JComboBox<String>(timeList60);
		panel.add(ssCB24);
		panel.add(new JLabel("초  ~ ")); // "Second ~ "

		// End Year
		eYCB24 = new JComboBox<String>(yearList);
		eYCB24.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				dateChanger("e24");
			}
		});
		panel.add(eYCB24);
		panel.add(new JLabel("년")); // "Year"

		// End Month
		eMCB24 = new JComboBox<String>(monthList);
		eMCB24.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				dateChanger("e24");
			}
		});
		panel.add(eMCB24);
		panel.add(new JLabel("월")); // "Month"

		// End Date
		eDCB24 = new JComboBox<String>();
		panel.add(eDCB24);
		panel.add(new JLabel("일")); // "Date"

		// End Hour
		ehCB24 = new JComboBox<String>(hourList24);
		panel.add(ehCB24);
		panel.add(new JLabel("시")); // "Hour"

		// End Minute
		emCB24 = new JComboBox<String>(timeList60);
		panel.add(emCB24);
		panel.add(new JLabel("분")); // "Minute"

		// End Second
		esCB24 = new JComboBox<String>(timeList60);
		panel.add(esCB24);
		panel.add(new JLabel("초  ")); // "Second"

		// Work
		panel.add(new JLabel("할 일")); // "Work"
		workTF24 = new JTextField(10);
		panel.add(workTF24);

		return panel;
	}

	/*
	 * This function add schedule, cancel schedule panel
	 */
	private JPanel addScheduleCancelSchedule() {
		JPanel panel = new JPanel(new GridLayout(1, 2));

		addButton = new JButton("추가"); // "Add Schedule"
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (type12RB.isSelected() == true) {
					addSchedule(12);
				} else if (type24RB.isSelected() == true) {
					addSchedule(24);
				}
			}
		});

		cancelButton = new JButton("취소"); // "Cancel"
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				init();
			}
		});

		panel.add(addButton);
		panel.add(cancelButton);

		return panel;
	}

	/*
	 * This function is for add scheduler panel init
	 */
	private void init() {
		Now_DateTime_Controller dc = new Now_DateTime_Controller();

		type12RB.setSelected(true);
		sYCB12.setSelectedItem(dc.getNowYearString());
		sMCB12.setSelectedItem(dc.getNowMonthString());
		sDCB12.setSelectedItem(dc.getNowDateString());
		if (dc.getNowAmPm().equals("오전")) // "am"
			sAmRB.setSelected(true);
		else if (dc.getNowAmPm().equals("오후")) // "pm"
			sPmRB.setSelected(true);
		shCB12.setSelectedItem(dc.getNowHourString(12));
		smCB12.setSelectedItem("00");
		ssCB12.setSelectedItem("00");

		eYCB12.setSelectedItem(dc.getNowYearString());
		eMCB12.setSelectedItem(dc.getNowMonthString());
		eDCB12.setSelectedItem(dc.getNowDateString());
		if (dc.getNowAmPm().equals("오전")) // "am"
			eAmRB.setSelected(true);
		else if (dc.getNowAmPm().equals("오후")) // "pm"
			ePmRB.setSelected(true);
		ehCB12.setSelectedItem(dc.getNowHourString(12));
		emCB12.setSelectedItem("00");
		esCB12.setSelectedItem("59");
		workTF12.setText("");

		sYCB24.setSelectedItem(dc.getNowYearString());
		sMCB24.setSelectedItem(dc.getNowMonthString());
		sDCB24.setSelectedItem(dc.getNowDateString());
		shCB24.setSelectedItem(dc.getNowHourString(24));
		smCB24.setSelectedItem("00");
		ssCB24.setSelectedItem("00");

		eYCB24.setSelectedItem(dc.getNowYearString());
		eMCB24.setSelectedItem(dc.getNowMonthString());
		eDCB24.setSelectedItem(dc.getNowDateString());
		ehCB24.setSelectedItem(dc.getNowHourString(24));
		emCB24.setSelectedItem("00");
		esCB24.setSelectedItem("59");
		workTF24.setText("");

		type12Enabled();
	}

	/*
	 * This function is for type12 enabled
	 */
	private void type12Enabled() {
		sYCB12.setEnabled(true);
		sMCB12.setEnabled(true);
		sDCB12.setEnabled(true);
		sAmRB.setEnabled(true);
		sPmRB.setEnabled(true);
		shCB12.setEnabled(true);
		smCB12.setEnabled(true);
		ssCB12.setEnabled(true);
		eYCB12.setEnabled(true);
		eMCB12.setEnabled(true);
		eDCB12.setEnabled(true);
		eAmRB.setEnabled(true);
		ePmRB.setEnabled(true);
		ehCB12.setEnabled(true);
		emCB12.setEnabled(true);
		esCB12.setEnabled(true);
		workTF12.setEnabled(true);

		sYCB24.setEnabled(false);
		sMCB24.setEnabled(false);
		sDCB24.setEnabled(false);
		shCB24.setEnabled(false);
		smCB24.setEnabled(false);
		ssCB24.setEnabled(false);
		eYCB24.setEnabled(false);
		eMCB24.setEnabled(false);
		eDCB24.setEnabled(false);
		ehCB24.setEnabled(false);
		emCB24.setEnabled(false);
		esCB24.setEnabled(false);
		workTF24.setEnabled(false);
	}

	/*
	 * This function is for type24 enabled
	 */
	private void type24Enabled() {
		sYCB12.setEnabled(false);
		sMCB12.setEnabled(false);
		sDCB12.setEnabled(false);
		sAmRB.setEnabled(false);
		sPmRB.setEnabled(false);
		shCB12.setEnabled(false);
		smCB12.setEnabled(false);
		ssCB12.setEnabled(false);
		eYCB12.setEnabled(false);
		eMCB12.setEnabled(false);
		eDCB12.setEnabled(false);
		eAmRB.setEnabled(false);
		ePmRB.setEnabled(false);
		ehCB12.setEnabled(false);
		emCB12.setEnabled(false);
		esCB12.setEnabled(false);
		workTF12.setEnabled(false);

		sYCB24.setEnabled(true);
		sMCB24.setEnabled(true);
		sDCB24.setEnabled(true);
		shCB24.setEnabled(true);
		smCB24.setEnabled(true);
		ssCB24.setEnabled(true);
		eYCB24.setEnabled(true);
		eMCB24.setEnabled(true);
		eDCB24.setEnabled(true);
		ehCB24.setEnabled(true);
		emCB24.setEnabled(true);
		esCB24.setEnabled(true);
		workTF24.setEnabled(true);
	}

	/*
	 * This function is for converting day_JComboBox to leap years.
	 * 
	 * @param String type: "s12" or "e12" or "s24" or "e24"
	 */
	private void dateChanger(String type) {
		JComboBox<String> yearCB = null;
		JComboBox<String> monthCB = null;
		JComboBox<String> dateCB = null;

		if (type == "s12") {
			yearCB = sYCB12;
			monthCB = sMCB12;
			dateCB = sDCB12;
		} else if (type == "e12") {
			yearCB = eYCB12;
			monthCB = eMCB12;
			dateCB = eDCB12;
		} else if (type == "s24") {
			yearCB = sYCB24;
			monthCB = sMCB24;
			dateCB = sDCB24;
		} else if (type == "e24") {
			yearCB = eYCB24;
			monthCB = eMCB24;
			dateCB = eDCB24;
		}

		Object selectedDate = dateCB.getSelectedItem();
		int selectedYear = Integer.parseInt(yearCB.getSelectedItem().toString());
		int selectedMonth = Integer.parseInt(monthCB.getSelectedItem().toString());

		DefaultComboBoxModel<String> model;
		if (selectedMonth != 2) {
			if (selectedMonth % 2 == 1) {
				model = new DefaultComboBoxModel<String>(dateList31);
			} else
				model = new DefaultComboBoxModel<String>(dateList30);
		} else {
			if (selectedYear % 4 == 0 && selectedYear % 100 != 0 || selectedYear % 400 == 0)
				model = new DefaultComboBoxModel<String>(dateList28);
			else
				model = new DefaultComboBoxModel<String>(dateList29);
		}
		dateCB.setModel(model);

		try {
			dateCB.setSelectedItem(selectedDate);
		} catch (Exception e) {
			dateCB.setSelectedIndex(0);
		}
	}

	/*
	 * This function is for adding a schedule
	 * 
	 * @ param int type: 12 or 24
	 */
	private void addSchedule(int type) {
		String sYStr = null, sMStr = null, sDStr = null, shStr = null, smStr = null, ssStr = null, eYStr = null,
				eMStr = null, eDStr = null, ehStr = null, emStr = null, esStr = null, work = null;

		if (type == 12) {
			sYStr = sYCB12.getSelectedItem().toString();
			sMStr = sMCB12.getSelectedItem().toString();
			sDStr = sDCB12.getSelectedItem().toString();
			if (sAmRB.isSelected() == true) {
				shStr = String.format("%02d", (shCB12.getSelectedIndex()));
			} else if (sPmRB.isSelected() == true) {
				shStr = Integer.toString((12 + shCB12.getSelectedIndex()));
			}
			smStr = smCB12.getSelectedItem().toString();
			ssStr = ssCB12.getSelectedItem().toString();
			eYStr = eYCB12.getSelectedItem().toString();
			eMStr = eMCB12.getSelectedItem().toString();
			eDStr = eDCB12.getSelectedItem().toString();
			if (eAmRB.isSelected() == true) {
				ehStr = String.format("%02d", (ehCB12.getSelectedIndex()));
			} else if (ePmRB.isSelected() == true) {
				ehStr = Integer.toString((12 + ehCB12.getSelectedIndex()));
			}
			emStr = emCB12.getSelectedItem().toString();
			esStr = esCB12.getSelectedItem().toString();
			work = workTF12.getText();
		} else if (type == 24) {
			sYStr = sYCB24.getSelectedItem().toString();
			sMStr = sMCB24.getSelectedItem().toString();
			sDStr = sDCB24.getSelectedItem().toString();
			shStr = shCB24.getSelectedItem().toString();
			smStr = smCB24.getSelectedItem().toString();
			ssStr = ssCB24.getSelectedItem().toString();
			eYStr = eYCB24.getSelectedItem().toString();
			eMStr = eMCB24.getSelectedItem().toString();
			eDStr = eDCB24.getSelectedItem().toString();
			ehStr = ehCB24.getSelectedItem().toString();
			emStr = emCB24.getSelectedItem().toString();
			esStr = esCB24.getSelectedItem().toString();
			work = workTF24.getText();
		}

		String newSchedule = sYStr + sMStr + sDStr + shStr + smStr + ssStr + "~" + eYStr + eMStr + eDStr + ehStr + emStr
				+ esStr + ":" + work;
		fc.addSchedule(newSchedule);
	}
}