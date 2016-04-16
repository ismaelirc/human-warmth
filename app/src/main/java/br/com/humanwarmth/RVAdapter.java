package br.com.humanwarmth;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.DoacaoViewHolder> {

    public static class DoacaoViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView doacaoEndereco;
        TextView doacaoDescricao;

        DoacaoViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            doacaoEndereco = (TextView)itemView.findViewById(R.id.doacao_endereco);
            doacaoDescricao = (TextView)itemView.findViewById(R.id.doacao_descricao);

        }
    }

    List<Doacao> doacoes;

    RVAdapter(List<Doacao> doacoes){
        this.doacoes = doacoes;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public DoacaoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_doacao_rv, viewGroup, false);
        DoacaoViewHolder dvh = new DoacaoViewHolder(v);
        return dvh;
    }

    @Override
    public void onBindViewHolder(DoacaoViewHolder personViewHolder, int i) {
        personViewHolder.doacaoDescricao.setText(doacoes.get(i).getDescricao());
        personViewHolder.doacaoEndereco.setText(doacoes.get(i).getEndereco());

    }

    @Override
    public int getItemCount() {
        return doacoes.size();
    }
}
