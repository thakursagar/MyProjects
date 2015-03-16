using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using DeveloperTest.TreeClasses;

namespace DeveloperTest
{
    public interface IDeveloperTest
    {
        int FindNthPrimeNumber(int n);

        bool OccursDuring(DateTime appointment1Start, DateTime appointment1End,
            DateTime appointment2Start, DateTime appointment2End);

        int FindSequence(string[] haystack, string[] needle);

        TreeNode BuildTree(IEnumerable<Input> inputs, TreeNode parentNode = null);
    }
}
