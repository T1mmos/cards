package gent.timdemey.cards.base.gui.dialog;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import gent.timdemey.cards.base.net.PingbackHandler;
import gent.timdemey.cards.base.net.ServerPinger;
import gent.timdemey.cards.base.processing.CLT_JoinServer;
import net.miginfocom.swing.MigLayout;

public class JoinServerDialog {

    private static final class ConnectionInfo {
        private final InetAddress ip;
        private final int port;

        public ConnectionInfo(InetAddress ip, int port) {
            this.ip = ip;
            this.port = port;
        }

        @Override
        public String toString() {
            return ip.getHostAddress() + ":" + port;
        }
    }

    private static final class ServerInfo {
        private final String name;
        private final String id;

        public ServerInfo(String name, String id) {
            this.name = name;
            this.id = id;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private final ServerPinger pinger;
    private final PingbackHandler handler = (id, name, ip, port) -> SwingUtilities
            .invokeLater(() -> onServerAdded(id, name, ip, port));
    private final ListSelectionListener selLstr = e -> onSelectionChanged();
    private final MouseListener mouseLstr = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            onClick(e);
        }
    };

    private final JDialog dialog;
    private final DefaultTableModel model;
    private final JButton b_join, b_cancel;
    private final JTable t_servers;

    public JoinServerDialog(ServerPinger pinger) {
        this.dialog = new JDialog((Frame) null, "Join Server", true);
        this.pinger = pinger;
        this.model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.b_join = new JButton("Join");
        this.b_cancel = new JButton("Cancel");
        this.t_servers = new JTable(model);

        model.setColumnIdentifiers(new String[] { "Name", "IP address" });
        b_join.addActionListener(e -> onJoinClicked());
        b_cancel.addActionListener(e -> onCancelClicked());
        b_join.setEnabled(false);
        t_servers.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void show() {
        pinger.addPingbackHandler(handler);
        pinger.ping();
        t_servers.addMouseListener(mouseLstr);
        t_servers.getSelectionModel().addListSelectionListener(selLstr);

        JPanel content = new JPanel(new MigLayout());
        content.add(new JScrollPane(t_servers), "height 100:150:max, push, grow, wrap");
        content.add(new JSeparator(), "span, pushx, grow, wrap");
        content.add(b_join, "sg buts, span, split 2, center");
        content.add(b_cancel, "sg buts, wrap");

        dialog.setContentPane(content);
        dialog.setMinimumSize(new Dimension(300, 200));
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        t_servers.getSelectionModel().removeListSelectionListener(selLstr);
        t_servers.removeMouseListener(mouseLstr);
        pinger.removePingbackHandler(handler);
    }

    private void onServerAdded(String id, String name, InetAddress ip, int port) {
        model.addRow(new Object[] { new ServerInfo(name, id), new ConnectionInfo(ip, port) });
        model.fireTableDataChanged();
    }

    private void onJoinClicked() {
        int idx = t_servers.getSelectionModel().getMinSelectionIndex();
        join(idx);
    }

    private void onCancelClicked() {
        dialog.setVisible(false);
    }

    private void onSelectionChanged() {
        b_join.setEnabled(t_servers.getSelectionModel().getMinSelectionIndex() != -1);
    }

    private void onClick(MouseEvent e) {
        if (e.getClickCount() == 2) {
            join(t_servers.rowAtPoint(e.getPoint()));
        }
    }

    private void join(int idx) {
        if (idx != -1){
            ServerInfo srvInfo = (ServerInfo) t_servers.getModel().getValueAt(idx, 0);
            ConnectionInfo connInfo = (ConnectionInfo) t_servers.getModel().getValueAt(idx, 1);
            dialog.setVisible(false);
            new CLT_JoinServer(connInfo.ip, connInfo.port, srvInfo.id, "Tim").process();
        }
    }
}
