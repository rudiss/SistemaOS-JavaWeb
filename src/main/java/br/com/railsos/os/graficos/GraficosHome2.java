import br.com.railsos.os.util.FabricaConexao;
import br.com.railsos.os.util.exception.ErroSistema;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Thamires
 */
@ManagedBean
public class GraficosHome2 implements Serializable {

    @PostConstruct
    public void init() {
        mostrarGraficos();
    }

    private PieChartModel graficoOrdensAbertas;

    public PieChartModel getGraficoOrdensAbertas() {
        return graficoOrdensAbertas;
    }

    public int getTotalOrdensAbertas() {
        return totalOrdensAbertas;
    }

    public void setTotalOrdensAbertas(int totalOrdensAbertas) {
        this.totalOrdensAbertas = totalOrdensAbertas;
    }

    public int getNumeroOrdensAbertas() {
        return numeroOrdensAbertas;
    }

    public void setNumeroOrdensAbertas(int numeroOrdensAbertas) {
        this.numeroOrdensAbertas = numeroOrdensAbertas;
    }

    public String getMensagemAbertas() {
        return mensagemAbertas;
    }

    public void setMensagemAbertas(String mensagemAbertas) {
        this.mensagemAbertas = mensagemAbertas;
    }

    public void setGraficoOrdensAbertas(PieChartModel graficoOrdensAbertas) {
        this.graficoOrdensAbertas = graficoOrdensAbertas;
    }
    private PieChartModel graficoOrdensEncerradas;
    private int totalOrdensEncerradas;
    private int numeroOrdensEncerradas;
    private String mensagemEncerradas;
    private int totalOrdensAbertas;
    private int numeroOrdensAbertas;
    private String mensagemAbertas;

    public String getMensagemEncerradas() {
        return mensagemEncerradas;
    }

    public void setMensagemEncerradas(String mensagemEncerradas) {
        this.mensagemEncerradas = mensagemEncerradas;
    }

    public int getTotalOrdensEncerradas() {
        return totalOrdensEncerradas;
    }

    public void setTotalOrdensEncerradas(int totalOrdensEncerradas) {
        this.totalOrdensEncerradas = totalOrdensEncerradas;
    }

    public int getNumeroOrdensEncerradas() {
        return numeroOrdensEncerradas;
    }

    public void setNumeroOrdensEncerradas(int numeroOrdensEncerradas) {
        this.numeroOrdensEncerradas = numeroOrdensEncerradas;
    }

    public PieChartModel getGraficoOrdensEncerradas() {
        return graficoOrdensEncerradas;
    }

    private void mostrarGraficos() {

        graficoOrdensEncerradas();
        graficoOrdensAbertas();

    }

    private void graficoOrdensEncerradas() {

        try {
            Connection conexao = FabricaConexao.getConexao();
            String sql = "SELECT COUNT(ordemservico.atendente_id) as total, funcionario.nome";
            sql = sql + " from ordemservico INNER JOIN";
            sql = sql + " funcionario on ordemservico.atendente_id = funcionario.nome";
            sql = sql + " where status = 'Encerrado' ";
            sql = sql + " GROUP by ordemservico.atendente_id, funcionario.nome";
            PreparedStatement ps = null;
            try {
                ps = conexao.prepareStatement(sql);
            } catch (SQLException ex) {
                Logger.getLogger(GraficosHome2.class.getName()).log(Level.SEVERE, null, ex);
            }
            ResultSet resultSet = null;
            try {
                resultSet = ps.executeQuery();
            } catch (SQLException ex) {
                Logger.getLogger(GraficosHome2.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {

                graficoOrdensEncerradas = new PieChartModel();
                graficoOrdensEncerradas.setTitle("Gráfico de Ordens de Serviço Encerradas por Funcionário");
                graficoOrdensEncerradas.setLegendPosition("e");
                graficoOrdensEncerradas.setFill(false);
                graficoOrdensEncerradas.setShowDataLabels(true);
                graficoOrdensEncerradas.setDiameter(230);

                while (resultSet.next()) {
                    numeroOrdensEncerradas = resultSet.getInt("total");
                    graficoOrdensEncerradas.set("Funcionário: " + resultSet.getString("nome") + " - Total de Ordens Realizadas: " + numeroOrdensEncerradas, (resultSet.getInt("total")));
                    totalOrdensEncerradas = totalOrdensEncerradas + resultSet.getInt("total");
                }

                mensagemEncerradas = "Total de Ordens Encerradas: " + totalOrdensEncerradas;

            } catch (SQLException ex) {
                Logger.getLogger(GraficosHome2.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                FabricaConexao.fecharConexao();
            } catch (ErroSistema ex) {
                Logger.getLogger(GraficosHome2.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ErroSistema ex) {
            Logger.getLogger(GraficosHome2.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void graficoOrdensAbertas() {

        try {
            Connection conexao = FabricaConexao.getConexao();
            String sql = "SELECT COUNT( ordemservico.atendente_id) as total, funcionario.nome";
            sql = sql + " from ordemservico INNER JOIN";
            sql = sql + " funcionario on ordemservico.atendente_id = funcionario.nome";
            sql = sql + " where status = 'Aberta' ";
            sql = sql + " GROUP by ordemservico.atendente_id, funcionario.nome";
            PreparedStatement ps = null;
            try {
                ps = conexao.prepareStatement(sql);
            } catch (SQLException ex) {
                Logger.getLogger(GraficosHome2.class.getName()).log(Level.SEVERE, null, ex);
            }
            ResultSet resultSet = null;
            try {
                resultSet = ps.executeQuery();
            } catch (SQLException ex) {
                Logger.getLogger(GraficosHome2.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {

                graficoOrdensAbertas = new PieChartModel();
                graficoOrdensAbertas.setTitle("Gráfico de Ordens de Serviço Abertas por Funcionário");
                graficoOrdensAbertas.setLegendPosition("e");
                graficoOrdensAbertas.setFill(false);
                graficoOrdensAbertas.setShowDataLabels(true);
                graficoOrdensAbertas.setDiameter(230);

                while (resultSet.next()) {
                    numeroOrdensAbertas = resultSet.getInt("total");
                    graficoOrdensAbertas.set("Funcionário: " + resultSet.getString("nome") + " - Total de Ordens Abertas: " + numeroOrdensAbertas, (resultSet.getInt("total")));
                    totalOrdensAbertas = totalOrdensAbertas + resultSet.getInt("total");
                }

                mensagemAbertas = "Total de Ordens Abertas: " + totalOrdensAbertas;

            } catch (SQLException ex) {
                Logger.getLogger(GraficosHome2.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                FabricaConexao.fecharConexao();
            } catch (ErroSistema ex) {
                Logger.getLogger(GraficosHome2.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ErroSistema ex) {
            Logger.getLogger(GraficosHome2.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}