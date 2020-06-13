import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.*;
import java.io.*;
import javax.swing.event.*;

public class SwingCalendar extends JFrame implements ActionListener {
	// Data

	static HashMap<String, ScheduleFile> schedule = new HashMap<>();
	static HashMap<String, String> mywork = new HashMap<>();
	Mywork mw;
	// Bottom
	Background b = new Background();
	JPanel allp = new JPanel();
	// North
	JPanel topPane = new JPanel();
	JButton prevBtn = new JButton("◀");
	JButton nextBtn = new JButton("▶");
	JLabel yearLbl = new JLabel("년");
	JLabel monthLbl = new JLabel("월");

	JComboBox<Integer> yearCombo = new JComboBox<Integer>();
	DefaultComboBoxModel<Integer> yearModel = new DefaultComboBoxModel<Integer>();
	JComboBox<Integer> monthCombo = new JComboBox<Integer>();
	DefaultComboBoxModel<Integer> monthModel = new DefaultComboBoxModel<Integer>();

	// North + a
	JPanel forc = new JPanel();
	JTextField alert = new JTextField(60);

	// Center
	JPanel NextSchedule = new JPanel();
	JPanel centerPane = new JPanel(new BorderLayout(0, 0));
	JPanel titlePane = new JPanel(new GridLayout(1, 7));
	String titleStr[] = { "일", "월", "화", "수", "목", "금", "토" };
	JPanel datePane = new JPanel(new GridLayout(0, 7));
	Calendar now;

	// Center + a
	JPanel forc1 = new JPanel();
	JPanel sch = new JPanel();
	BoxLayout box = new BoxLayout(sch, BoxLayout.Y_AXIS);
	Box color = new Box(BoxLayout.Y_AXIS);
	JPanel thismonth = new JPanel();
	Vector<String> vs = new Vector<>(); // 일정 내용
	Vector<JLabel> vi = new Vector<>(); // 일정 아이콘
	JSplitPane jsp = new JSplitPane();

	JTextArea work = new JTextArea(6, 5);
	JScrollPane scr;
	JScrollPane scr1;
	
	int p = 1; //index
	int year;
	int month;
	int date;
	int ty, tm, td; // today
	int lastDate;

	public SwingCalendar() {
		GetFile file = new GetFile(schedule, mywork);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		now = Calendar.getInstance(); // 현재 날짜

		year = now.get(Calendar.YEAR);

		month = now.get(Calendar.MONTH) + 1;

		date = now.get(Calendar.DATE);

		ty = year;
		tm = month;
		td = date;
		topPane.add(prevBtn);

		for (int i = year - 100; i <= year + 50; i++) {

			yearModel.addElement(i);

		}

		yearCombo.setModel(yearModel);

		yearCombo.setSelectedItem(year); // 현재 년도 선택

		topPane.add(yearCombo);

		topPane.add(yearLbl);

		for (int i = 1; i <= 12; i++) {

			monthModel.addElement(i);

		}

		monthCombo.setModel(monthModel);

		monthCombo.setSelectedItem(month); // 현재 월 선택

		topPane.add(monthCombo);

		topPane.add(monthLbl);

		topPane.add(nextBtn);
		
		topPane.setBackground(Color.PINK); // 상단 판넬 색
		// 레이아웃
		setContentPane(b);
		allp.setLayout(new BorderLayout(0, 15));
		allp.setOpaque(false);

		// North +a 상단 판 forc(Border)
		forc.setLayout(new BorderLayout()); // 달월 조정메뉴와 알림판
		forc.add(topPane, "North");
		
		/*
		 * 알림판 : NextScheldule Panel (FlowLayout) ㄴ JLabel bell(icon) ㄴ JTextField
		 * alert(알림내용)
		 */
		
		NextSchedule.setLayout(new FlowLayout());
		ImageIcon p = new ImageIcon("images/alarm.png");
		JLabel bell = new JLabel(p);

		
		alert.setFont(new Font("굴림", Font.BOLD, 20));
		alert.setHorizontalAlignment((int) CENTER_ALIGNMENT);// !!!!!! 가운데 정렬
		
		//일정알림 text
		if(schedule.get("" + ty + tm +"-"+ td) != null)
			alert.setText((schedule.get("" + ty + tm +"-"+ td).getTitle()));
		else
			alert.setText("오늘은 일정이 없습니다");
		

		NextSchedule.add(bell);
		NextSchedule.add(alert);
		NextSchedule.setBackground(Color.WHITE);
		forc.add(NextSchedule);

		allp.add(forc, "North");

// Center 달력.

		titlePane.setBackground(Color.LIGHT_GRAY);

		for (int i = 0; i < titleStr.length; i++) {
			JLabel lbl = new JLabel(titleStr[i], JLabel.CENTER);
			lbl.setBorder(new LineBorder(Color.black, 1));

			if (i == 0) {
				lbl.setForeground(Color.red);
			} else if (i == 6) {
				lbl.setForeground(Color.blue);
			}
			titlePane.add(lbl);
		}

		centerPane.add(titlePane, "North"); // 요일
		centerPane.add(datePane, "Center"); // 일

		dayPrint(year, month);// 날짜 출력

//Center +a

		/*
		 * sch -> 왼쪽 스케줄판 ㄴ word1 "이번달일정" ㄴ thismonth(일정) ㄴcolor(라벨 boxlayout)
		 * ㄴlst(jlist 일정 이름) ㄴ word2 "할일" ㄴ word(JTextArea 할일)
		 */
		forc1.setLayout(new GridLayout(1, 2, 50, 0)); // 전반적인 컴포넌트
		forc1.setOpaque(false);
		centerPane.setBorder(new LineBorder(Color.black, 2)); // 디자인 // 달력 라인

		sch.setLayout(box);
		sch.setOpaque(false);

//thismonth -> 이번달 할일메모 in sch
		// 디자인
		thismonth.setOpaque(true);
		thismonth.setBackground(Color.white);
		thismonth.setBorder(new LineBorder(Color.BLACK, 2));
		scr = new JScrollPane(thismonth);
		scr.setPreferredSize(new Dimension(200,250)); //중요!
		
		// 이번달 일정 관리
		mw = new Mywork(year, month);
		printThismonth();	// 일정 아이콘 부분

		JPanel pw1 = new JPanel();
		JPanel pw2 = new JPanel();
		pw1.setOpaque(true);
		pw2.setOpaque(true);
		pw1.setBackground(Color.pink);
		pw2.setBackground(Color.pink);

		JLabel word1 = new JLabel("이번 달 일정");
		word1.setFont(new Font("고딕", Font.BOLD, 25));
		word1.setAlignmentX(CENTER_ALIGNMENT);
		JLabel word2 = new JLabel("할일");
		word2.setFont(new Font("고딕", Font.BOLD, 25));
		word2.setAlignmentX(CENTER_ALIGNMENT);

		pw1.add(word1);
		pw2.add(word2);

		// sch 부분 배치관련
		sch.add(pw1); // thismonth
		word1.setBackground(Color.PINK);
		word1.setOpaque(true);
		sch.add(scr);//thismonth
		sch.add(Box.createVerticalStrut(20));

		sch.add(pw2); // area
		word2.setBackground(Color.PINK);
		word2.setOpaque(true);
		
		work.setFont(new Font("고딕", Font.PLAIN, 15));
		work.setBorder(new LineBorder(Color.BLACK, 2));
		scr1 = new JScrollPane(work);
		sch.add(scr1);
		if (mywork.get("" + year + month) == null)
			work.setText("할 일을 적어주세요\n");
		else
			work.setText(mywork.get(year + "" + month));
		
		//work enter event
		work.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						File file  = new File("data/" + year + month + ".txt");
						BufferedWriter bufw = new BufferedWriter(new FileWriter(file));
						
						if(file.isFile()&&file.canWrite()) {
							bufw.write(work.getText());
							bufw.close();
						}
								 
					}catch(IOException e1) {
						System.out.println(e1);
					}
				}
				
			}

//			@Override
//			public void keyTyped(KeyEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
			
		});
		forc1.add(centerPane);
		forc1.add(sch);
		allp.add(forc1);
		setSize(1600, 1000);
		setVisible(true);

		prevBtn.addActionListener(this);
		nextBtn.addActionListener(this);
		yearCombo.addActionListener(this);
		monthCombo.addActionListener(this);
	}

	// Overring

	public void actionPerformed(ActionEvent ae) {
		b.getParent().repaint();
		Object obj = ae.getSource();

		if (obj instanceof JButton) {

			JButton eventBtn = (JButton) obj;

			int yy = (Integer) yearCombo.getSelectedItem();
			int mm = (Integer) monthCombo.getSelectedItem();

			if (eventBtn.equals(prevBtn)) { // 전달
				if (mm == 1) {
					yy--;
					mm = 12;
				} else {
					mm--;
				}

			} else if (eventBtn.equals(nextBtn)) { // 다음달
				if (mm == 12) {
					yy++;
					mm = 1;
				} else {
					mm++;
				}
			}
			yearCombo.setSelectedItem(yy);
			monthCombo.setSelectedItem(mm);
		} else if (obj instanceof JComboBox) { // 콤보박스 이벤트 발생시

			createDayStart();

		}

	}

	public void createDayStart() {

		datePane.setVisible(false); // 패널 숨기기

		datePane.removeAll(); // 날짜 출력한 라벨 지우기

		dayPrint((Integer) yearCombo.getSelectedItem(), (Integer) monthCombo.getSelectedItem());

		createSetStart();
		datePane.setVisible(true); // 패널 재출력
	}

	// 배경 및 스케줄 변경
	public void createSetStart() {

		b.setVisible(false); // 패널 숨기기
		
		thismonth.setVisible(false); // 패널 숨기기
		thismonth.removeAll(); // 날짜 출력한 라벨 지우기
		
		work.setVisible(false);
		work.removeAll();
		
		year = ((Integer) yearCombo.getSelectedItem()).intValue();
		month = ((Integer) monthCombo.getSelectedItem()).intValue();
		
		work.setText(mywork.get(year + "" + month));
		mw = new Mywork(year, month);
		work.setText(mywork.get("" + year + month));
		
		printThismonth();
		
		b.getParent().repaint();
		b.setVisible(true); // 패널 재출력
		thismonth.setVisible(true); // 패널 재출력
		work.setVisible(true);
	}

	public void dayPrint(int y, int m) {// 일을 출력함.

		Calendar cal = Calendar.getInstance();

		cal.set(y, m - 1, 1); // 출력할 첫날의 객체 만든다.

		int week = cal.get(Calendar.DAY_OF_WEEK); // 1일에 대한 요일 일요일 : 0

		lastDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 그 달의 마지막 날

		for (int i = 1; i < week; i++) { // 날짜 출력 전까지의 공백 출력
			datePane.add(new JLabel(" "));

		}
		datePane.setBackground(Color.white);
		datePane.setOpaque(true);

		for (int i = 1; i <= lastDate; i++) {

			JButton lbl = new JButton(String.valueOf(i));									 // 일을 추가 여기에 이벤트
			lbl.setFont(new Font("바탕", Font.BOLD, 20));
			if ((y == ty) && (m == tm) && (i == td))
				lbl.setBorder(new LineBorder(Color.magenta, 5));
			else
				lbl.setBorder(new LineBorder(Color.BLACK, 1));
			
			if(schedule.get("" + y + m + "-"+ i ) != null) {
				ScheduleFile sc = schedule.get(""+y + m + "-"+ i);
				lbl.setBackground(new Color(sc.getR(),sc.getG(),sc.getB()));
			}
			cal.set(y, m - 1, i);

			int outWeek = cal.get(Calendar.DAY_OF_WEEK);

			if (outWeek == 1) {

				lbl.setForeground(Color.red);

			} else if (outWeek == 7) {

				lbl.setForeground(Color.BLUE);

			}

			lbl.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JButton btn = (JButton) e.getSource();
					new CalendarSchedule(year, month, Integer.parseInt(btn.getText()));
				}
			});
			datePane.add(lbl);
			add(allp, "Center");
		}

	}


	public void printThismonth() {
		 Box color = Box.createVerticalBox();
		//JPanel color = new JPanel(new BoxLayout());
		for (int i = 0; i < vi.size(); i++) {
			color.add(vi.get(i));
		}
		thismonth.add(color);
		JList lst = new JList(vs);
		lst.setFont(new Font("고딕", Font.PLAIN, 24)); // 일정내용관리 리스트
		thismonth.add(lst);

	}
	

	class Background extends JPanel { // 배경 그리기용 판넬
		private ImageIcon im;

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if ((month == 1) || (month == 7) || (month == 10) || (month == 11) || (month == 12))
				im = new ImageIcon("images/" + month + ".jpg");
			else
				im = new ImageIcon("images/" + month + ".png");
			Image m = im.getImage();
			g.drawImage(m, 0, 0, getWidth(), getHeight(), this);
			setOpaque(false);
		}

	}

	class Mywork { // 일정의 아이콘과 내용관리 (벡터)
		private String st = "";
		Color col;
		Mywork(int y, int m) {
			ScheduleFile k;
			String key;
			
			vs.clear();
			vi.clear();

			// 파일 불러오기(개수)
			for (int i = 1; i < lastDate; i++) {
				key = "" + y + m + "-"+ i ; //년월-일
				if (schedule.get(key) == null) {
					continue;
				} else {
					k = schedule.get(key);
					// 제목
					st = k.getTitle();
					// 라벨색
					JLabel icon = new JLabel("■");
					icon.setFont(new Font("고딕", Font.PLAIN, 26));
					col = new Color(k.getR(), k.getG(), k.getB());
					icon.setForeground(col);
					//여기서 button 색 지정을 해야 될 것 같은데.
					// 파일저장
					vs.add(st);
					vi.add(icon);
				}

			}
		}

	}

	public static void main(String[] args) {

		new SwingCalendar();

	}

}
