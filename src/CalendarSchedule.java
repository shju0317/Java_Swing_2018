import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class CalendarSchedule extends JFrame {
	Container c = getContentPane();
	static int year;
	static int month;
	static int day;
	Color color;
	String title, place, establishment, classification, people, time, timed, content;
	
	Box centerPanel = Box.createVerticalBox();

	public CalendarSchedule(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;

		// North
		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.PINK);
		JLabel yearLabel = new JLabel(year + "");
		yearLabel.setFont(new Font("굴림", Font.BOLD, 20));
		JLabel yearLabeln = new JLabel("년");
		yearLabeln.setFont(new Font("굴림", Font.BOLD, 20));
		JLabel monthLabel = new JLabel(month + "");
		monthLabel.setFont(new Font("굴림", Font.BOLD, 20));
		JLabel monthLabeln = new JLabel("월");
		monthLabeln.setFont(new Font("굴림", Font.BOLD, 20));
		JLabel dayLabel = new JLabel(day + "");
		dayLabel.setFont(new Font("굴림", Font.BOLD, 20));
		JLabel dayLabeln = new JLabel("일");
		dayLabeln.setFont(new Font("굴림", Font.BOLD, 20));
		JButton addTodo = new JButton("+");
		addTodo.setBackground(Color.LIGHT_GRAY);

		ScheduleFile sc = SwingCalendar.schedule.get("" + year + month + "-" + day);
		if (sc != null) {
			addTodo.setEnabled(false);
			color = new Color(sc.getR(), sc.getG(), sc.getB());
			title = sc.getTitle();
			place = sc.getArea();
			establishment = sc.getWork();
			classification = sc.getCategory();
			people = sc.getWho();
			time = sc.getStart();
			timed = sc.getTime();
			content = sc.getInfo();
			new makeTodo(color, title, place, establishment, classification, people, time, timed, content);
			centerPanel.revalidate();
		}

		addTodo.addActionListener(new ActionListener() { // 파일 추가
			@Override
			public void actionPerformed(ActionEvent e) {				
					new addSchedule();
			}
		});

		northPanel.add(Box.createHorizontalStrut(10));
		northPanel.add(yearLabel);
		northPanel.add(yearLabeln);
		northPanel.add(monthLabel);
		northPanel.add(monthLabeln);
		northPanel.add(dayLabel);
		northPanel.add(dayLabeln);
		northPanel.add(Box.createHorizontalStrut(10));
		northPanel.add(addTodo);

		c.add(northPanel, BorderLayout.NORTH);

		// Center
		c.add(new JScrollPane(centerPanel), BorderLayout.CENTER);
		
		setTitle(year + "년 " + month + "월 " + day + "일 일정");
		setSize(400, 250);
		setVisible(true);
	}

	class makeTodo extends JPanel {
		String title, place, establishment, classification, people, time, timed, content;
		Color color;

		makeTodo(Color color, String title, String place, String establishment, String classification, String people,
				String time, String timed, String content) {
			this.color = color;
			this.title = title;
			this.place = place;
			this.establishment = establishment;
			this.classification = classification;
			this.people = people;
			this.time = time;
			this.timed = timed;
			this.content = content;

			JPanel toDoThing = new JPanel();
			toDoThing.setLayout(new BorderLayout());
			toDoThing.setBorder(new LineBorder(Color.BLACK, 2));
			Box northTodo = Box.createHorizontalBox();
			northTodo.setPreferredSize(new Dimension(100, 40));
			Box centerTodo = Box.createVerticalBox();

			// North
			JLabel colorBox = new JLabel("■");
			colorBox.setForeground(color);
			JLabel toDoTitle = new JLabel(title);
			toDoTitle.setFont(new Font("굴림", 0, 20));
			JButton deleteTodo = new JButton("-");
			deleteTodo.addActionListener(new ActionListener() { // 파일 삭제
				@Override
				public void actionPerformed(ActionEvent e) {
					centerPanel.setVisible(false);
					centerPanel.remove(toDoThing);					
					File file = new File("schedule/" + year + month + "-" + day + ".txt");
					file.delete();
				}
			});
			deleteTodo.setBackground(Color.GRAY);

			northTodo.add(Box.createHorizontalStrut(10));
			northTodo.add(colorBox);
			northTodo.add(Box.createHorizontalStrut(5));
			northTodo.add(toDoTitle);
			northTodo.add(Box.createHorizontalGlue());
			northTodo.add(deleteTodo);
			northTodo.add(Box.createHorizontalStrut(10));

			// Center
			JLabel placeL = new JLabel("장소");
			placeL.setFont(new Font("Gothic", Font.BOLD, 15));
			JLabel placeConL = new JLabel(place);
			placeConL.setFont(new Font("Gothic", 0, 15));
			JLabel establishmentL = new JLabel("기관");
			establishmentL.setFont(new Font("Gothic", Font.BOLD, 15));
			JLabel establishmentConL = new JLabel(establishment);
			establishmentConL.setFont(new Font("Gothic", 0, 15));
			JLabel classificationL = new JLabel("분류");
			classificationL.setFont(new Font("Gothic", Font.BOLD, 15));
			JLabel classificationConL = new JLabel(classification);
			classificationConL.setFont(new Font("Gothic", 0, 15));
			JLabel peopleL = new JLabel("대상");
			peopleL.setFont(new Font("Gothic", Font.BOLD, 15));
			JLabel peopleConL = new JLabel(people);
			peopleConL.setFont(new Font("Gothic", 0, 15));
			JLabel timeL = new JLabel("시각");
			timeL.setFont(new Font("Gothic", Font.BOLD, 15));
			JLabel timeConL = new JLabel(time);
			timeConL.setFont(new Font("Gothic", 0, 15));
			JLabel timedL = new JLabel("시간");
			timedL.setFont(new Font("Gothic", Font.BOLD, 15));
			JLabel timedConL = new JLabel(timed);
			timedConL.setFont(new Font("Gothic", 0, 15));
			JLabel contentL = new JLabel("내용");
			contentL.setFont(new Font("Gothic", Font.BOLD, 18));
			JLabel contentConL = new JLabel(content);
			contentConL.setFont(new Font("Gothic", 0, 15));

			JPanel information = new JPanel(new GridLayout(3, 2));

			Box b1 = Box.createHorizontalBox();
			b1.setAlignmentX(LEFT_ALIGNMENT);
			Box b2 = Box.createHorizontalBox();
			b2.setAlignmentX(LEFT_ALIGNMENT);
			Box b3 = Box.createHorizontalBox();
			b3.setAlignmentX(LEFT_ALIGNMENT);
			Box b4 = Box.createHorizontalBox();
			b4.setAlignmentX(LEFT_ALIGNMENT);
			Box b5 = Box.createHorizontalBox();
			b5.setAlignmentX(LEFT_ALIGNMENT);
			Box b6 = Box.createHorizontalBox();
			b6.setAlignmentX(LEFT_ALIGNMENT);
			Box b7 = Box.createHorizontalBox();
			b6.setAlignmentX(LEFT_ALIGNMENT);
			Box b8 = Box.createHorizontalBox();
			b6.setAlignmentX(LEFT_ALIGNMENT);

			b1.add(Box.createHorizontalStrut(5));
			b1.add(placeL);
			b1.add(Box.createHorizontalStrut(5));
			b1.add(placeConL);

			b2.add(Box.createHorizontalStrut(5));
			b2.add(establishmentL);
			b2.add(Box.createHorizontalStrut(5));
			b2.add(establishmentConL);

			b3.add(Box.createHorizontalStrut(5));
			b3.add(classificationL);
			b3.add(Box.createHorizontalStrut(5));
			b3.add(classificationConL);

			b4.add(Box.createHorizontalStrut(5));
			b4.add(peopleL);
			b4.add(Box.createHorizontalStrut(5));
			b4.add(peopleConL);

			b5.add(Box.createHorizontalStrut(5));
			b5.add(timeL);
			b5.add(Box.createHorizontalStrut(5));
			b5.add(timeConL);

			b6.add(Box.createHorizontalStrut(5));
			b6.add(timedL);
			b6.add(Box.createHorizontalStrut(5));
			b6.add(timedConL);

			JPanel informationC = new JPanel(new BorderLayout());

			b7.add(Box.createHorizontalStrut(5));
			b7.add(contentL);
			informationC.add(b7, BorderLayout.NORTH);

			b8.add(Box.createHorizontalStrut(5));
			b8.add(contentConL);
			informationC.add(b8, BorderLayout.CENTER);

			information.add(b1);
			information.add(b2);
			information.add(b3);
			information.add(b4);
			information.add(b5);
			information.add(b6);

			centerTodo.add(information);
			centerTodo.add(informationC);

			toDoThing.add(northTodo, BorderLayout.NORTH);
			toDoThing.add(centerTodo, BorderLayout.CENTER);

			centerPanel.add(toDoThing);

		}
	}
}
