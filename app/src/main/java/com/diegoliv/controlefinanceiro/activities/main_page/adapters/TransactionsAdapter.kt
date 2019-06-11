package com.diegoliv.controlefinanceiro.activities.main_page.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diegoliv.controlefinanceiro.R
import com.diegoliv.controlefinanceiro.models.Transaction

class TransactionsAdapter(private val context: Context, val transactions: List<Transaction>): RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder>() {

    override fun getItemCount(): Int {
        return transactions.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.transaction_item, parent, false)
        Log.d("recycler", "onCreateViewHolder")
        return TransactionViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]

        val nameTextView = holder.itemView.findViewById<TextView>(R.id.nameTextView)
        nameTextView.text = transaction.name

        val dataTextView = holder.itemView.findViewById<TextView>(R.id.dataTextView)
        dataTextView.text = transaction.date.toString()

        val valueTextView = holder.itemView.findViewById<TextView>(R.id.valueTextView)
        valueTextView.text = "R$ ${transaction.value}"

        val balanceBeforeTextView = holder.itemView.findViewById<TextView>(R.id.valueBeforeTextView)
        balanceBeforeTextView.text = "R$ ${transaction.balanceBefore}"

        val balanceAfterTextView = holder.itemView.findViewById<TextView>(R.id.valueAfterTextView)
        balanceAfterTextView.text = "R$ ${transaction.balanceAfter}"
    }

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}