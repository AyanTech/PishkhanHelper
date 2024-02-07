package ir.ayantech.pishkhanhelper.scanner

typealias BillCallBack = (billId: String, paymentId: String) -> Unit

object BillRecognizer {

    fun handleResult(billId: String, paymentId: String, billCallBack: BillCallBack) {
        val finalBillId = "0".repeat(13 - billId.length) + billId
        val finalPaymentId = "0".repeat(13 - paymentId.length) + paymentId
        if (!recognitionQBillType(finalBillId))
            throw Exception("شناسه قبض اشتباه است.")
        if (!recognitionQBillPay(finalPaymentId))
            throw Exception("شناسه پرداخت اشتباه است.")
        billCallBack(finalBillId, finalPaymentId)
    }

    fun handleResult(result: String, billCallBack: BillCallBack) {
        if (result.length != 26)
            throw Exception("بارکد شناسایی نشد.")
        if (!checkReturnCode(result))
            throw Exception("بارکد شناسایی نشد.")
        val billId = result.substring(0, 13)
        val paymentId = result.substring(13, result.length)
        handleResult(billId, paymentId, billCallBack)
    }

    private fun recognitionQBillType(result: String): Boolean {
        var count = 2
        var sum = 0
        val R: Int
        val controlNo: Int

        val newBillId = IntArray(result.length)

        val sumNo = IntArray(result.length)

        for (i in result.indices) {
            newBillId[i] = result[i] - '0'
        }
        // --------------------------------------
        for (i in newBillId.size - 2 downTo 0) {
            sumNo[i] = newBillId[i] * count

            if (count < 7)
                count += 1
            else if (count == 7)
                count = 2
        }
        // ---------------------------------------
        for (i in sumNo.indices) {
            sum += sumNo[i]
        }
        R = sum % 11
        controlNo = if (R == 0 || R == 1) 0 else 11 - R
        return controlNo == newBillId[newBillId.size - 1]
    }

    private fun recognitionQBillPay(result: String): Boolean {
        var count = 2
        var sum = 0
        val R: Int
        var controlNo = 0

        val newBillId = IntArray(result.length)

        val sumNo = IntArray(result.length)

        for (i in result.indices) {
            newBillId[i] = result[i] - '0'
        }
        for (i in newBillId.size - 3 downTo 0) {
            sumNo[i] = newBillId[i] * count

            if (count < 7)
                count += 1
            else if (count == 7)
                count = 2
        }
        for (i in sumNo.indices) {
            sum += sumNo[i]
        }
        R = sum % 11
        controlNo = if (R == 0 || R == 1) 0 else 11 - R
        return controlNo == newBillId[newBillId.size - 2]
    }

    private fun checkReturnCode(code: String): Boolean {
        for (element in code) {
            return element.toInt() in 48..57
        }
        return false
    }
}