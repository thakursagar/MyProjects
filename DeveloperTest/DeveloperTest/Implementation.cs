using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using DeveloperTest.TreeClasses;

namespace DeveloperTest
{
    public class Implementation : IDeveloperTest
    {
        public int FindNthPrimeNumber(int n)
        {
            int i, count;
            for (i = 2, count = 0; count < n; ++i)
            {
                if (checkPrime(i))
                {
                    //Console.WriteLine(i);
                    ++count;
                }
            }
            return i-1;
        }
        public Boolean checkPrime(int no)
        {
            int j = (int)Math.Sqrt(no) + 1; // stopping at square root of number
            for (int i = 2; i < j; ++i)
            {
                if (no % i == 0)
                {
                    return false;
                }
            }
            return true;

        }


        public bool OccursDuring(DateTime appointment1Start, DateTime appointment1End,
            DateTime appointment2Start, DateTime appointment2End)
        {
            if (appointment2Start > appointment1Start && appointment2Start < appointment1End) // check if appt2 has start time between appt1 start and appt1 end
                return true;
            else if (appointment2End > appointment1Start && appointment2End < appointment1End) // check if appt2 has end time between appt1 start and appt1 end
                return true;
            else if (appointment1Start == appointment2Start || appointment1End == appointment2End)
                return true;
            else return false;
        }

        public int FindSequence(string[] haystack, string[] needle)
        {
            if (haystack.Length == 0 || needle.Length == 0) // check if any one of the arrays is empty
                return -1;
            string temp = needle[0];
            Boolean flag = false;
            for (int i = 0; i < haystack.Length; i++)
            {
                if (temp.Equals(haystack[i]))// check if first element of needle matches with any element in haystack
                {
                    if (needle.Length == 1)
                    return i;
                    int k = i;
                    for (int j = 1; j < needle.Length; j++) // check if next needle element is also present
                    {
                        k++;
                        if (k >= haystack.Length) // check if that was the last element of the haystack array
                        {
                            flag = false;
                            break;
                        }

                        else if (needle[j].Equals(haystack[k])) // if the next needle element also equals next haystack element, make flag true and check for next
                            flag = true;
                        else flag = false;
                    }
                    if (flag)
                        return i;
                }                
                
            }
            return -1;
        }


        public TreeNode BuildTree(IEnumerable<Input> inputs, TreeNode parentNode = null)
        {
            
            
           foreach(var input in inputs)
           {
               if (input.ParentId == 0 || input.ParentId == null) // check for parent
                   parentNode = new TreeNode(input.Id);
               else if (input.ParentId == parentNode.Id) // check if root's child
               {
                   TreeNode child = new TreeNode(input.Id);
                   parentNode.AddChild(child);

               }
               else
               {
                   var treeNodes = new List<TreeNode>();
                   parentNode.Traverse(x => treeNodes.Add(x)); // traverse from root and put the children in the treeNodes list
                   foreach (TreeNode child in treeNodes) // check all treeNoes
                   {
                       if (child.Id == input.ParentId)
                       {
                           TreeNode newChild = new TreeNode(input.Id);
                           child.AddChild(newChild);
                       }
                   }
               }
           }
           return parentNode;

        }

    }
}
