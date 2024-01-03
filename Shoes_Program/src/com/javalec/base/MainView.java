package com.javalec.base;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.javalec.function.Dao_MainView;
import com.javalec.function.Dto_Mainview;
import com.javalec.function.ShareVar;
import com.javalec.product.MainView_Info;

public class MainView extends JDialog {
	/*
	 * Description : 메인뷰 페이지
	 * Date : 2023.12.31
	 * Author : 박지환
	 * Update :
	 * 		1. 브랜드 이름별 검색기능 추가
	 * 		2. 테이블크기 확장
	 */

	//테이블안에 글씨 중앙정렬 기능 추가필요
	//상세페이지랑 연결필요->내일 상세페이지 맡은사람한테 실행되나 물어보기
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JComboBox cbSelection;
	private JTextField tfSelection;
	private JButton btnQuery;
	private JScrollPane scrollPane;
	private JTable InnerTable;

	// table
	private final DefaultTableModel OuterTable = new DefaultTableModel();
//			private JLabel lblImage;
//			private JButton btnFilePath;
//			private JTextField tfFilePath;

	// -- file 정리
	ArrayList<Dto_Mainview> beanList = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MainView dialog = new MainView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public MainView() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				tableInit();
				queryAction(); // <--***************************************************

			}
		});
		setBounds(ShareVar.position_window_x, ShareVar.position_window_y, 800, 600);;
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getCbSelection());
		contentPanel.add(getTfSelection());
		contentPanel.add(getBtnQuery());
		contentPanel.add(getScrollPane());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}
	}

	private JComboBox getCbSelection() {
		if (cbSelection == null) {
			cbSelection = new JComboBox();
			cbSelection.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			cbSelection.setModel(new DefaultComboBoxModel(new String[] { "브랜드", "이름" }));
			cbSelection.setBounds(104, 36, 134, 27);
		}
		return cbSelection;
	}

	private JTextField getTfSelection() {
		if (tfSelection == null) {
			tfSelection = new JTextField();
			tfSelection.setBounds(250, 37, 303, 26);
			tfSelection.setColumns(10);
		}
		return tfSelection;
	}

	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton("검색");
			btnQuery.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					searchAction();
				}
			});
			btnQuery.setBounds(565, 35, 117, 29);
		}
		return btnQuery;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {

				}
			});
			scrollPane.setBounds(52, 102, 688, 420);
			scrollPane.setViewportView(getInnerTable());
		}
		return scrollPane;
	}

	private JTable getInnerTable() {
		if (InnerTable == null) {
			InnerTable = new JTable() { // <--****************
				public Class getColumnClass(int column) { // <--****************
					return (column == 0) ? Icon.class : Object.class; // <--****************
				}
			};
			InnerTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					tableClick();
					goToDetailInfo();

				}
			});
			InnerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			InnerTable.setRowHeight(150); // <--***************************************************
			InnerTable.setModel(OuterTable); // <--***************************************************
		}
		return InnerTable;
	}

	private void tableInit() {
		// table column명 정하
		OuterTable.addColumn("상세사진");
		OuterTable.addColumn("브랜드");
		OuterTable.addColumn("상품이름");
		OuterTable.addColumn("가격");

		OuterTable.setColumnCount(4);
		// table column 크기 정하
		int colNo = 0;
		TableColumn col = InnerTable.getColumnModel().getColumn(colNo);
		int width = 300;
		col.setPreferredWidth(width);

		colNo = 1;
		col = InnerTable.getColumnModel().getColumn(colNo);
		width = 100;
		col.setPreferredWidth(width);
		colNo = 2;
		col = InnerTable.getColumnModel().getColumn(colNo);
		width = 100;
		col.setPreferredWidth(width);
		colNo = 3;
		col = InnerTable.getColumnModel().getColumn(colNo);
		width = 180;
		col.setPreferredWidth(width);

		InnerTable.setAutoResizeMode(InnerTable.AUTO_RESIZE_OFF); // 사이즈 고정이라서 스크롤바가 나온다

		int i = OuterTable.getRowCount();
		for (int j = 1; j <= i; j++) {
			OuterTable.removeRow(0);

		}

	}

	// 초기 Table 이미지,브랜드,이름,가격 표시(윈도우 열릴때)
	private void queryAction() {
		Dao_MainView dbAction = new Dao_MainView();
		beanList = dbAction.dao_pjh_AllProductMainviews();

		int listCount = beanList.size();

		for (int index = 0; index < listCount; index++) {
			ImageIcon icon = new ImageIcon("./" + beanList.get(index).getFake_filename());
			Object[] tempData = { icon, beanList.get(index).getPbrand(), beanList.get(index).getPname(),
					beanList.get(index).getPprice() };
			OuterTable.addRow(tempData);
		}

	}

	// 검색: 텍스트필드에서 받아온 브랜드, 이름을 활용
	private void searchAction() {
		tableInit();
		String searchText = tfSelection.getText();
		if (cbSelection.getSelectedItem().toString().equals("브랜드")) {

			Dao_MainView dbAction = new Dao_MainView();

			beanList = dbAction.dao_pjh_BrandSearchProductMainviews(searchText);

			int listCount = beanList.size();

			for (int index = 0; index < listCount; index++) {
				ImageIcon icon = new ImageIcon("./" + beanList.get(index).getFake_filename());
				Object[] tempData = { icon, beanList.get(index).getPbrand(), beanList.get(index).getPname(),
						beanList.get(index).getPprice() };
				OuterTable.addRow(tempData);
			}

		} else if (cbSelection.getSelectedItem().toString().equals("이름")) {
			Dao_MainView dbAction = new Dao_MainView();
			beanList = dbAction.dao_pjh_NameSearchProductMainviews(searchText);

			int listCount = beanList.size();

			for (int index = 0; index < listCount; index++) {
				ImageIcon icon = new ImageIcon("./" + beanList.get(index).getFake_filename());
				Object[] tempData = { icon, beanList.get(index).getPbrand(), beanList.get(index).getPname(),
						beanList.get(index).getPprice() };
				OuterTable.addRow(tempData);
			}
		}

	}

	private void tableClick() {
		Dao_MainView dao_MainView = new Dao_MainView();
		dao_MainView.p_seqArrayList(); // p_seq가 들어있는 ArrayList 가져옴
		
		InnerTable.getSelectedRow();	// InnerTable을 클릭했을 때 몇번째 row 인지 가져옴
		
		MainView_Info.clickSeq = dao_MainView.p_seqArrayList().get(InnerTable.getSelectedRow());
		
		// MainView_Info.clickSeq를 p_seq가 들어있는 ArrayList에서 InnerTable을 클릭했을 때 몇번째 row인지 비교 후 클릭된 p_seq값만 가져옴
	}

	private void goToDetailInfo() {
		// MainView 클래스에서 DetailInfoPage 객체를 생성하고 보여주기
		DetailInfoPage detailInfoPage = new DetailInfoPage();
		detailInfoPage.setVisible(true);
		// this.setVisible(false);
	}

}// ---------------------------end