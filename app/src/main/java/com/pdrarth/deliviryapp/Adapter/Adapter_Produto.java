package com.pdrarth.deliviryapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pdrarth.deliviryapp.Model.Produto;
import com.pdrarth.deliviryapp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_Produto extends RecyclerView.Adapter<Adapter_Produto.ProdutsViewHolder> {
    private Context context;
    private List<Produto> produtoslist;

    public Adapter_Produto(Context context, List<Produto> produtoslist) {
        this.context = context;
        this.produtoslist = produtoslist;
    }

    @NonNull
    @Override
    //criado as vizualizacoes da lista
    public ProdutsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemlista;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        itemlista = layoutInflater.inflate(R.layout.list_produts_cardview, parent, false);
        return new ProdutsViewHolder(itemlista);
    }

    @Override
    //vai exibir as lisa do produtos
    public void onBindViewHolder(@NonNull ProdutsViewHolder holder, int position) {

        holder.imagem_produto.setImageResource(produtoslist.get(position).getFoto());
        holder.nome.setText(produtoslist.get(position).getNome());
        holder.preco.setText(produtoslist.get(position).getPreco());

    }

    @Override
    public int getItemCount() {
        return produtoslist.size();
    }

    public class ProdutsViewHolder extends  RecyclerView.ViewHolder {

        private TextView nome,preco,descricao;
        private CircleImageView imagem_produto;


        public ProdutsViewHolder(@NonNull View itemView) {
            super(itemView);
            imagem_produto = itemView.findViewById(R.id.circleImageView_produtos);
            nome = itemView.findViewById(R.id.nome_produtos);
            preco = itemView.findViewById(R.id.preco_produto);
        }
    }
}
