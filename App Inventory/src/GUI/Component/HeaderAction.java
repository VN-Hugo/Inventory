///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package GUI.Component;
//
///**
// *
// * @author Lenovo
// */
//public class HeaderAction  extends Panel{
//        JPanel panel = new JPanel(new MigLayout("insets 5 20 5 20", "[fill,230]push[][]"));
//
//        JTextField txtSearch = new JTextField();
//        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Search...");
//        txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("raven/modal/demo/icons/search.svg", 0.4f));
//        JButton cmdCreate = new JButton("Create");
//        JButton cmdEdit = new JButton("Edit");
//        JButton cmdDelete = new JButton("Delete");
//
//        cmdCreate.addActionListener(e -> showModal());
//        panel.add(txtSearch);
//        panel.add(cmdCreate);
//        panel.add(cmdEdit);
//        panel.add(cmdDelete);
//
//        panel.putClientProperty(FlatClientProperties.STYLE, "" +
//                "background:null;");
//        return panel;
//}
