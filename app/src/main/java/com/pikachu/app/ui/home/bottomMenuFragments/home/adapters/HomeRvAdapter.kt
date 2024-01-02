package com.pikachu.app.ui.home.bottomMenuFragments.home.adapters

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pikachu.app.R
import com.pikachu.app.databinding.FragmentAutoScrollingOptionsBinding
import com.pikachu.app.databinding.FragmentHomeRvAdItemsBinding
import com.pikachu.app.databinding.LayoutPetCardBinding
import com.pikachu.app.databinding.LayoutStoreRvItemBinding
import com.pikachu.app.network.responseModels.PetCard
import com.pikachu.app.network.responseModels.StoreCard
import java.util.Random

class HomeRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var petCards: List<PetCard>? = null
    var storeCards: List<StoreCard>? = null

    //val types = IntArray(20){java.util.Random().nextInt(2)}
    val types = arrayOf(1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1)
    //private var displayList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val bindingScrollingOptions =
            FragmentAutoScrollingOptionsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        val bindingAdItem =
            FragmentHomeRvAdItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val bindingPetCard =
            LayoutPetCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val bindingStoreItem =
            LayoutStoreRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return when (ItemType.values()[viewType]) {
            ItemType.FULL_WIDTH_FRAGMENT -> {
                // Inflate layout for full-width fragment
                FragmentViewHolder(bindingScrollingOptions)
            }

            ItemType.FULL_WIDTH_ADS -> {
                // Inflate layout for full-width ads
                AdsViewHolder(bindingAdItem)
            }

            ItemType.SINGLE_CARD -> {
                // Inflate layout for single card
                SingleCardViewHolder(bindingStoreItem)
            }

            ItemType.TWO_CARDS -> {
                TwoCardViewHolder(bindingPetCard)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // Bind data for various view types
        when (holder) {
            is FragmentViewHolder -> {}
            is AdsViewHolder -> {}
            is SingleCardViewHolder -> {}
            is TwoCardViewHolder -> {
                val random = Random().nextInt(2)
                if (random == 0)
                    holder.binding.petCardPhotoIV.setImageResource(R.drawable.cct)
                else
                    holder.binding.petCardPhotoIV.setImageResource(R.drawable.catcat)
                holder.binding.petCardPhotoIV.clipToOutline = true
            }
        }

    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun getItemViewType(position: Int): Int {
        val randomChoice = if (types[position] == 0) {
            // First choice
            ItemType.SINGLE_CARD
        } else {
            // Second choice
            ItemType.TWO_CARDS
        }
        val itemType = when (position) {
            0 -> ItemType.FULL_WIDTH_FRAGMENT
            else -> randomChoice
        }
        // Determine and return view type based on the enum
        return ItemType.values().indexOfFirst { it.name == itemType.toString() }
    }

    class FragmentViewHolder(val binding: FragmentAutoScrollingOptionsBinding) :
        RecyclerView.ViewHolder(binding.root)

    class AdsViewHolder(val binding: FragmentHomeRvAdItemsBinding) :
        RecyclerView.ViewHolder(binding.root)

    class SingleCardViewHolder(val binding: LayoutStoreRvItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class TwoCardViewHolder(val binding: LayoutPetCardBinding) :
        RecyclerView.ViewHolder(binding.root)

}

enum class ItemType {
    FULL_WIDTH_FRAGMENT,
    FULL_WIDTH_ADS,
    SINGLE_CARD,
    TWO_CARDS
}

class GridSpacingItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if(position==0){

        }
        else {
            outRect.apply {
                left = spacing
                right = spacing
                top = spacing
                bottom = spacing
            }
        }
    }
}

