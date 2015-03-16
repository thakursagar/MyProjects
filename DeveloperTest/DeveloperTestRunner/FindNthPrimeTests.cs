using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DeveloperTestRunner
{
    internal class FindNthPrimeTests : Tests
    {
        public void FindNthPrimeTest1()
        {
            Tester.AreEqual(Implementation.FindNthPrimeNumber(5), 11);
        }

        public void FindNthPrimeTest2()
        {
            Tester.AreEqual(Implementation.FindNthPrimeNumber(10), 29);
            //Console.WriteLine(Implementation.FindNthPrimeNumber(1000000));
        }
        //public void FindNthPrimeTest3()
        //{
        //    Tester.AreEqual(Implementation.FindNthPrimeNumber(1000000), 15485863);
        //}   
    }
}
