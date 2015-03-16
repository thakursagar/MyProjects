using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using DeveloperTest;

namespace DeveloperTestRunner
{
    class Program
    {
        static void Main(string[] args)
        {
            var implementation = new Implementation();

            var nthPrimeTester = new QuestionTester();
            var nthPrimeTests = new FindNthPrimeTests
            {
                Implementation = implementation,
                Tester = nthPrimeTester
            };

            TestRunner.Run(nthPrimeTests);
            Console.WriteLine("FindNthPrime Tests:");
            Console.WriteLine(nthPrimeTester.GetTestResults());

            var appointmentTester = new QuestionTester();
            var appointmentTests = new AppointmentTests
            {
                Implementation = implementation,
                Tester = appointmentTester
            };

            TestRunner.Run(appointmentTests);
            Console.WriteLine("\r\nOccursDuring Tests:");
            Console.WriteLine(appointmentTester.GetTestResults());
            
            var findSequenceTester = new QuestionTester();
            var findSequenceTests = new FindSequenceTests
            {
                Implementation = implementation,
                Tester = findSequenceTester
            };

            TestRunner.Run(findSequenceTests);
            Console.WriteLine("\r\nFindSequence Tests:");
            Console.WriteLine(findSequenceTester.GetTestResults());

            var buildTreeTester = new QuestionTester();
            var buildTreeTests = new BuildTreeTests
            {
                Implementation = implementation,
                Tester = buildTreeTester
            };

            TestRunner.Run(buildTreeTests);
            Console.WriteLine("\r\nBuildTree Tests:");
            Console.WriteLine(buildTreeTester.GetTestResults());

            Console.WriteLine("Press any key . . .");
            Console.ReadKey();
        }
    }
}
